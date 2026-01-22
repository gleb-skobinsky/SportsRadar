package org.violet.violetapp.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.scope.Scope
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual fun Scope.getThemeStorage(
    preferencesFileName: String,
    jvmChildDirectory: String,
): Storage {
    return DataStoreStorage(
        internalStore = createIosDataStore(preferencesFileName)
    )
}

/**
 * Creates an iOS-specific [DataStore] for preferences storage.
 *
 * This function initializes a DataStore that stores preferences in the app's documents
 * directory on iOS. The preferences file will be created in the app's sandboxed documents
 * directory, following iOS storage conventions.
 *
 * @param preferencesFileName The name of the preferences file to create.
 * @return A configured [DataStore] instance for iOS.
 */
@OptIn(ExperimentalForeignApi::class)
internal fun createIosDataStore(
    preferencesFileName: String,
): DataStore<Preferences> = createDataStore(
    path = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
    )!!.path + "/$preferencesFileName"
)