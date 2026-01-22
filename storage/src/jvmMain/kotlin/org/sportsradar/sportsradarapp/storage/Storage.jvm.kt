package org.sportsradar.sportsradarapp.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.serialization.json.Json
import org.koin.core.scope.Scope
import java.io.File

/**
 * Creates a JVM-specific [DataStore] for preferences storage.
 *
 * This function initializes a DataStore that stores preferences in a subdirectory
 * within the user's home directory on JVM platforms (desktop applications).
 * The directory structure follows common conventions for application data storage
 * on desktop systems.
 *
 * @param preferencesFileName The name of the preferences file to create.
 * @param jvmChildDirectory The name of the subdirectory to create within the user's home directory.
 * @return A configured [DataStore] instance for JVM platforms.
 */
internal fun createJvmDataStore(
    preferencesFileName: String,
    jvmChildDirectory: String,
): DataStore<Preferences> = createDataStore(
    path = run {
        val baseDir = File(
            System.getProperty("user.home"),
            jvmChildDirectory
        )
        baseDir.mkdirs()
        File(baseDir, preferencesFileName).absolutePath
    }
)

actual fun Scope.getStorage(
    json: Json,
    useSession: Boolean,
    preferencesFileName: String,
    jvmChildDirectory: String,
): Storage {
    return DataStoreStorage(
        internalStore = createJvmDataStore(
            preferencesFileName = preferencesFileName,
            jvmChildDirectory = jvmChildDirectory
        ),
        json = json
    )
}