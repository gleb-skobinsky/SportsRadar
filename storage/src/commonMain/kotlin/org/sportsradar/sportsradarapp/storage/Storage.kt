package org.sportsradar.sportsradarapp.storage

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import org.koin.core.scope.Scope

/**
 * Internal interface for theme storage operations across different platforms.
 */
interface Storage {
    val json: Json

    fun subscribeToString(key: String): Flow<String?>

    suspend fun getString(key: String): String?

    fun getStringBlocking(key: String): String?

    suspend fun setString(key: String, value: String?)

    fun subscribeToInt(key: String): Flow<Int?>

    suspend fun getInt(key: String): Int?

    fun getIntBlocking(key: String): Int?

    suspend fun setInt(key: String, value: Int?)

    fun subscribeToFloat(key: String): Flow<Float?>

    suspend fun getFloat(key: String): Float?

    fun getFloatBlocking(key: String): Float?

    suspend fun setFloat(key: String, value: Float?)

    fun subscribeToDouble(key: String): Flow<Double?>

    suspend fun getDouble(key: String): Double?

    fun getDoubleBlocking(key: String): Double?

    suspend fun setDouble(key: String, value: Double?)

    fun subscribeToLong(key: String): Flow<Long?>

    suspend fun getLong(key: String): Long?

    fun getLongBlocking(key: String): Long?

    suspend fun setLong(key: String, value: Long?)

    fun subscribeToBoolean(key: String): Flow<Boolean?>

    suspend fun getBoolean(key: String): Boolean?

    fun getBooleanBlocking(key: String): Boolean?

    suspend fun setBoolean(key: String, value: Boolean?)

    suspend fun clearAll()
}


suspend inline operator fun <reified T : Any> Storage.set(key: String, value: T?) {
    when (value) {
        is String -> setString(key, value)
        is Int -> setInt(key, value)
        is Float -> setFloat(key, value)
        is Long -> setLong(key, value)
        is Double -> setDouble(key, value)
        is Boolean -> setBoolean(key, value)
        else -> setSerializable(key, value)
    }
}

suspend inline operator fun <reified T : Any> Storage.get(key: String): T? {
    return when (T::class) {
        String::class -> getString(key) as T?
        Int::class -> getInt(key) as T?
        Float::class -> getFloat(key) as T?
        Long::class -> getLong(key) as T?
        Double::class -> getDouble(key) as T?
        Boolean::class -> getBoolean(key) as T?
        else -> getSerializable<T>(key)
    }
}

inline fun <reified T : Any> Storage.getBlocking(key: String): T? {
    return when (T::class) {
        String::class -> getStringBlocking(key) as T?
        Int::class -> getIntBlocking(key) as T?
        Float::class -> getFloatBlocking(key) as T?
        Long::class -> getLongBlocking(key) as T?
        Double::class -> getDoubleBlocking(key) as T?
        Boolean::class -> getBooleanBlocking(key) as T?
        else -> getSerializableBlocking<T>(key)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : Any> Storage.subscribe(key: String): Flow<T?> {
    return when (T::class) {
        String::class -> subscribeToString(key) as Flow<T?>
        Int::class -> subscribeToInt(key) as Flow<T?>
        Float::class -> subscribeToFloat(key) as Flow<T?>
        Long::class -> subscribeToLong(key) as Flow<T?>
        Double::class -> subscribeToDouble(key) as Flow<T?>
        Boolean::class -> subscribeToBoolean(key) as Flow<T?>
        else -> subscribeToSerializable<T>(key)
    }
}

inline fun <reified T : Any> Storage.subscribeOrDefault(
    key: String,
    crossinline default: () -> T,
): Flow<T> {
    return subscribe<T>(key).map { it ?: default() }
}

@PublishedApi
internal suspend inline fun <reified T : Any> Storage.setSerializable(key: String, value: T?) {
    if (value == null) {
        setString(key, null)
    } else {
        val valueAsString = json.encodeToString<T?>(value)
        setString(key, valueAsString)
    }
}

@PublishedApi
internal suspend inline fun <reified T : Any> Storage.getSerializable(key: String): T? {
    val stringValue = getString(key) ?: return null
    return try {
        json.decodeFromString<T>(stringValue)
    } catch (_: Exception) {
        currentCoroutineContext().ensureActive()
        null
    }
}

@PublishedApi
internal inline fun <reified T : Any> Storage.getSerializableBlocking(key: String): T? {
    val stringValue = getStringBlocking(key) ?: return null
    return try {
        json.decodeFromString<T>(stringValue)
    } catch (_: Exception) {
        null
    }
}

@PublishedApi
internal inline fun <reified T : Any> Storage.subscribeToSerializable(key: String): Flow<T?> {
    return subscribeToString(key).map { newValue ->
        val stringValue = newValue ?: return@map null
        try {
            json.decodeFromString<T>(stringValue)
        } catch (_: Exception) {
            currentCoroutineContext().ensureActive()
            null
        }
    }.distinctUntilChanged()
}

/**
 * Creates a platform-specific [Storage] implementation for settings persistence.
 */
expect fun Scope.getStorage(
    json: Json,
    useSession: Boolean,
    preferencesFileName: String,
    jvmChildDirectory: String,
): Storage