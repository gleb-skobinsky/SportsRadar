package org.sportsradar.sportsradarapp.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

/**
 * Creates a [DataStore] for preferences using the specified file path.
 *
 * This function initializes a DataStore that persists preferences to a file at the
 * given path. The DataStore uses protocol buffers for efficient serialization.
 *
 * @param path The absolute file path where preferences should be stored.
 * @return A configured [DataStore] instance for preferences.
 */
internal fun createDataStore(path: String): DataStore<Preferences> =
    PreferenceDataStoreFactory.createWithPath(
        produceFile = { path.toPath() }
    )

/**
 * A [Storage] implementation that uses Jetpack DataStore for theme persistence.
 *
 * This implementation provides reactive theme storage using Jetpack DataStore,
 * which offers type-safe, asynchronous data storage with a Flow-based API.
 * It's suitable for Android and other platforms that support DataStore.
 */
internal class DataStoreStorage(
    private val internalStore: DataStore<Preferences>,
    override val json: Json,
) : Storage {

    override fun subscribeToString(key: String): Flow<String?> = internalStore.data.map {
        it[stringPreferencesKey(key)]
    }.distinctUntilChanged()

    override suspend fun setString(key: String, value: String?) {
        editByKey(value, stringPreferencesKey(key))
    }

    override suspend fun getString(key: String): String? {
        return internalStore.data.firstOrNull()?.get(stringPreferencesKey(key))
    }

    override fun subscribeToInt(key: String): Flow<Int?> {
        return internalStore.data.map {
            it[intPreferencesKey(key)]
        }.distinctUntilChanged()
    }

    override suspend fun getInt(key: String): Int? {
        return internalStore.data.firstOrNull()?.get(intPreferencesKey(key))
    }

    override suspend fun setInt(key: String, value: Int?) {
        editByKey(value, intPreferencesKey(key))
    }

    override fun subscribeToFloat(key: String): Flow<Float?> {
        return internalStore.data.map {
            it[floatPreferencesKey(key)]
        }.distinctUntilChanged()
    }

    override suspend fun getFloat(key: String): Float? {
        return internalStore.data.firstOrNull()?.get(floatPreferencesKey(key))
    }

    override suspend fun setFloat(key: String, value: Float?) {
        editByKey(value, floatPreferencesKey(key))
    }

    override fun subscribeToLong(key: String): Flow<Long?> {
        return internalStore.data.map {
            it[longPreferencesKey(key)]
        }.distinctUntilChanged()
    }

    override suspend fun getLong(key: String): Long? {
        return internalStore.data.firstOrNull()?.get(longPreferencesKey(key))
    }

    override suspend fun setLong(key: String, value: Long?) {
        editByKey(value, longPreferencesKey(key))
    }

    override fun subscribeToDouble(key: String): Flow<Double?> {
        return internalStore.data.map {
            it[doublePreferencesKey(key)]
        }.distinctUntilChanged()
    }

    override suspend fun getDouble(key: String): Double? {
        return internalStore.data.firstOrNull()?.get(doublePreferencesKey(key))
    }

    override suspend fun setDouble(key: String, value: Double?) {
        editByKey(value, doublePreferencesKey(key))
    }

    override fun subscribeToBoolean(key: String): Flow<Boolean?> {
        return internalStore.data.map {
            it[booleanPreferencesKey(key)]
        }.distinctUntilChanged()
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return internalStore.data.firstOrNull()?.get(booleanPreferencesKey(key))
    }

    override suspend fun setBoolean(key: String, value: Boolean?) {
        editByKey(value, booleanPreferencesKey(key))
    }

    private suspend fun <T : Any> editByKey(value: T?, key: Preferences.Key<T>) {
        internalStore.edit {
            if (value == null) {
                it.remove(key)
            } else {
                it[key] = value
            }
        }
    }

    override suspend fun clearAll() {
        internalStore.edit { it.clear() }
    }
}
