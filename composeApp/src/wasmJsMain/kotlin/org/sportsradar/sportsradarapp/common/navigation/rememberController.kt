@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavGraphNavigator
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.DialogNavigator
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.savedState
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
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.long
import kotlinx.serialization.json.longOrNull
import org.w3c.dom.get
import org.w3c.dom.set

@Composable
actual fun rememberController(): NavHostController {
    val saver = remember { NavControllerWebSaver() }
    val controller = remember {
        createNavController().apply {
            restoreState(saver.restoreState())
        }
    }

    RegisterBeforeUnloadEffect {
        saver.saveState(controller.saveState())
    }
    return controller
}

private fun createNavController() =
    NavHostController().apply {
        navigatorProvider.addNavigator(ComposeNavGraphNavigator(navigatorProvider))
        navigatorProvider.addNavigator(ComposeNavigator())
        navigatorProvider.addNavigator(DialogNavigator())
    }


private class NavControllerWebSaver {
    fun saveState(savedState: SavedState?) {
        if (savedState == null) return
        val jsonObject = JsonObject(
            savedState.read { toMap() }.mapValues { toJson(it.value) }
        )
        sessionStorage[NAV_STATE_KEY] = jsonObject.toString()
    }

    fun restoreState(): SavedState? {
        val raw = sessionStorage[NAV_STATE_KEY] ?: return null
        val decodedMap = Json.parseToJsonElement(raw).jsonObject.mapValues {
            fromJson(it.value)
        }
        return savedState(decodedMap)
    }

    companion object {
        private const val NAV_STATE_KEY = "navhost_state_key"
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
        is IntArray -> JsonArray(value.map { toJson(it) })
        is SavedState -> JsonObject(
            value.read { toMap() }.entries.associate {
                it.key to toJson(it.value)
            }
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

        // TODO: Improve IntArray parsing?
        is JsonArray if (element.first() is JsonPrimitive && element.first().jsonPrimitive.intOrNull != null) -> {
            IntArray(element.size) {
                element[it].jsonPrimitive.int
            }
        }

        is JsonArray -> element.map { fromJson(it) }
        is JsonObject -> savedState(element.mapValues { fromJson(it.value) })
    }