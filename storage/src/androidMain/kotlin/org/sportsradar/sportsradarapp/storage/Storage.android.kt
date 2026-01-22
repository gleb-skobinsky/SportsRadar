package org.sportsradar.sportsradarapp.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.serialization.json.Json
import org.koin.core.scope.Scope

/**
 * Creates an Android-specific [DataStore] for preferences storage.
 *
 * This function initializes a DataStore that stores preferences in the app's internal
 * storage directory on Android. The preferences file will be created in the context's
 * files directory, making it private to the application.
 *
 * @param context The Android context used to access the app's files directory.
 * @param preferencesFileName The name of the preferences file to create.
 * @return A configured [DataStore] instance for Android.
 */
internal fun createAndroidDataStore(
    context: Context,
    preferencesFileName: String,
): DataStore<Preferences> = createDataStore(
    context.filesDir.resolve(preferencesFileName).absolutePath
)

actual fun Scope.getStorage(
    json: Json,
    useSession: Boolean,
    preferencesFileName: String,
    jvmChildDirectory: String,
): Storage {
    return DataStoreStorage(
        internalStore = createAndroidDataStore(
            context = get(),
            preferencesFileName = preferencesFileName
        ),
        json = json
    )
}