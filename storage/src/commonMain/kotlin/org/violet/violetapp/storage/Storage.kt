package org.violet.violetapp.storage

import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.scope.Scope

/**
 * Internal interface for theme storage operations across different platforms.
 *
 * This interface abstracts theme persistence mechanisms, allowing different platforms
 * to implement storage using their native solutions (SharedPreferences on Android,
 * UserDefaults on iOS, localStorage on Web, etc.).
 */
interface Storage {
    fun subscribeToString(key: String): Flow<String?>

    suspend fun getString(key: String): String?

    suspend fun setString(key: String, value: String?)

    fun subscribeToInt(key: String): Flow<Int?>

    suspend fun getInt(key: String): Int?

    suspend fun setInt(key: String, value: Int?)

    fun subscribeToFloat(key: String): Flow<Float?>

    suspend fun getFloat(key: String): Float?

    suspend fun setFloat(key: String, value: Float?)

    fun subscribeToDouble(key: String): Flow<Double?>

    suspend fun getDouble(key: String): Double?

    suspend fun setDouble(key: String, value: Double?)

    fun subscribeToLong(key: String): Flow<Long?>

    suspend fun getLong(key: String): Long?

    suspend fun setLong(key: String, value: Long?)

    fun subscribeToBoolean(key: String): Flow<Boolean?>

    suspend fun getBoolean(key: String): Boolean?

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

@PublishedApi
internal suspend inline fun <reified T : Any> Storage.setSerializable(key: String, value: T?) {
    if (value == null) {
        setString(key, null)
    } else {
        val valueAsString = DefaultJson.encodeToString<T?>(value)
        setString(key, valueAsString)
    }
}

@PublishedApi
internal suspend inline fun <reified T : Any> Storage.getSerializable(key: String): T? {
    val stringValue = getString(key) ?: return null
    return try {
        DefaultJson.decodeFromString<T>(stringValue)
    } catch (_: Exception) {
        currentCoroutineContext().ensureActive()
        null
    }
}

@PublishedApi
internal inline fun <reified T : Any> Storage.subscribeToSerializable(key: String): Flow<T?> {
    return subscribeToString(key).map { newValue ->
        val stringValue = newValue ?: return@map null
        try {
            DefaultJson.decodeFromString<T>(stringValue)
        } catch (_: Exception) {
            currentCoroutineContext().ensureActive()
            null
        }
    }.distinctUntilChanged()
}

@OptIn(ExperimentalSerializationApi::class)
@PublishedApi
internal val DefaultJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
    allowTrailingComma = true
}

/**
 * Creates a platform-specific [Storage] implementation for theme persistence.
 *
 * This expect function is implemented differently on each platform to use the
 * most appropriate storage mechanism available on that platform.
 *
 * @param preferencesFileName The name of the preferences file or storage key.
 * @param preferencesKey The key used to store the theme value within the storage.
 * @param jvmChildDirectory The subdirectory name for JVM platforms (used in user home directory).
 * @return A platform-specific [Storage] implementation.
 */
expect fun Scope.getStorage(
    useSession: Boolean,
    preferencesFileName: String,
    jvmChildDirectory: String = ".myapp",
): Storage