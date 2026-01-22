package org.violet.violetapp.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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
 *
 * @param internalStore The [DataStore] instance used for persistence operations.
 * @param preferencesKey The string key used to identify the theme preference within the store.
 */
internal class DataStoreStorage(
    private val internalStore: DataStore<Preferences>,
) : Storage {

    override fun subscribeByKey(key: String): Flow<String?> = internalStore.data.map {
        it[stringPreferencesKey(key)]
    }.distinctUntilChanged()

    override suspend fun setByKey(key: String, value: String?) {
        internalStore.edit {
            if (value == null) {
                it.remove(stringPreferencesKey(key))
            } else {
                it[stringPreferencesKey(key)] = value
            }
        }
    }

    override suspend fun clearAll() {
        internalStore.edit { it.clear() }
    }
}