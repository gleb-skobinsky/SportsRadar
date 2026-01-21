package io.github.themeanimator.storage

import kotlinx.coroutines.flow.Flow
import org.koin.core.scope.Scope

/**
 * Internal interface for theme storage operations across different platforms.
 *
 * This interface abstracts theme persistence mechanisms, allowing different platforms
 * to implement storage using their native solutions (SharedPreferences on Android,
 * UserDefaults on iOS, localStorage on Web, etc.).
 */
interface Storage {
    fun subscribeByKey(key: String): Flow<String?>

    suspend fun setByKey(key: String, value: String?)

    suspend fun clearAll()
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
expect fun Scope.getThemeStorage(
    preferencesFileName: String = "theme_animator.preferences_pb",
    jvmChildDirectory: String = ".myapp",
): Storage