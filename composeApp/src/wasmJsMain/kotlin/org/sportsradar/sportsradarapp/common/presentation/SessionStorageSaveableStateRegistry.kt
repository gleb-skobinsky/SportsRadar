package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.SaveableStateRegistry
import kotlinx.browser.sessionStorage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.double
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.int
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull

class SessionStorageSaveableStateRegistry(
    private val namespace: String = "compose_state"
) : SaveableStateRegistry {

    private val restored = mutableMapOf<String, List<JsonElement>>()
    private val providers = mutableMapOf<String, MutableList<() -> Any?>>()

    init {
        load()
    }

    override fun consumeRestored(key: String): Any? {
        val list = restored[key] ?: run {
            println("No value by key")
            return null
        }
        if (list.isEmpty()) {
            println("List provider is empty")
            return null
        }
        val head = list.first()
        restored[key] = list.drop(1)
        val returnValue = fromJson(head)
        println("Restored value: $returnValue")
        return returnValue
    }

    override fun registerProvider(
        key: String,
        valueProvider: () -> Any?
    ): SaveableStateRegistry.Entry {
        val list = providers.getOrPut(key) { mutableListOf() }
        list += valueProvider
        return object : SaveableStateRegistry.Entry {
            override fun unregister() {
                list -= valueProvider
                if (list.isEmpty()) providers.remove(key)
            }
        }
    }

    override fun canBeSaved(value: Any): Boolean {
        return canBeSavedImpl(value)
    }

    private fun canBeSavedImpl(value: Any?): Boolean {
        return when (value) {
            null,
            is Boolean,
            is Int,
            is Long,
            is Float,
            is Double,
            is String,
            is List<*>,
            is Map<*, *> -> true

            is MutableState<*> -> canBeSavedImpl(value.value)

            else -> false
        }
    }

    override fun performSave(): Map<String, List<Any?>> {
        println("Performing save")
        val jsonMap = providers.mapValues { entry ->
            entry.value.map { toJson(it()) }
        }
        save(jsonMap)
        return providers.mapValues { it.value.map { provider -> provider() } }
    }

    private fun save(data: Map<String, List<JsonElement>>) {
        val json = JsonObject(data.mapValues { JsonArray(it.value) })
        sessionStorage.setItem(namespace, json.toString())
    }

    private fun load() {
        val raw = sessionStorage.getItem(namespace) ?: return
        val json = Json.parseToJsonElement(raw).jsonObject
        json.forEach { (key, value) ->
            restored[key] = value.jsonArray.toList()
        }
    }

    private fun toJson(value: Any?): JsonElement =
        when (value) {
            null -> JsonNull
            is Boolean -> JsonPrimitive(value)
            is Int -> JsonPrimitive(value)
            is Long -> JsonPrimitive(value)
            is Float -> JsonPrimitive(value)
            is Double -> JsonPrimitive(value)
            is String -> JsonPrimitive(value)
            is List<*> -> JsonArray(value.map { toJson(it) })
            is Map<*, *> -> JsonObject(value.entries.associate {
                it.key.toString() to toJson(it.value)
            })
            is MutableState<*> -> JsonObject(
                mapOf(
                    "MutableState" to JsonPrimitive(true),
                    "value" to toJson(value.value)
                )
            )

            else -> error("Value $value is not saveable in Web SaveableStateRegistry")
        }

    private fun fromJson(element: JsonElement?): Any? =
        when (element) {
            null -> null
            is JsonNull -> null
            is JsonPrimitive -> when {
                element.isString -> element.content
                element.booleanOrNull != null -> element.boolean
                element.intOrNull != null -> element.int
                element.longOrNull != null -> element.long
                element.doubleOrNull != null -> element.double
                else -> element.content
            }

            is JsonArray -> element.map { fromJson(it) }
            is JsonObject if (element["MutableState"]?.jsonPrimitive?.booleanOrNull == true) -> {
                val wrappedValue = fromJson(element["value"]?.jsonPrimitive)
                mutableStateOf(wrappedValue)
            }
            is JsonObject -> element.mapValues { fromJson(it.value) }
        }
}

