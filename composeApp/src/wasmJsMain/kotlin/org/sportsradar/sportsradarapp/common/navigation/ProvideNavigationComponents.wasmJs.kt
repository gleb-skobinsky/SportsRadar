package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.LocalSaveableStateRegistry
import kotlinx.browser.window
import org.sportsradar.sportsradarapp.common.presentation.SessionStorageSaveableStateRegistry
import org.w3c.dom.events.Event

private const val BEFORE_UNLOAD_KEY = "beforeunload"

@Composable
internal actual fun ProvideNavigationComponents(
    navigator: KMPNavigator,
    content: @Composable (() -> Unit)
) {
    val registry = remember { SessionStorageSaveableStateRegistry() }
    CompositionLocalProvider(
        LocalKmpNavigator provides navigator,
        LocalSaveableStateRegistry provides registry,
        content = content
    )

    DisposableEffect(registry) {
        val listener: (Event) -> Unit = {
            registry.performSave()
        }

        window.addEventListener(BEFORE_UNLOAD_KEY, listener)

        onDispose {
            window.removeEventListener(BEFORE_UNLOAD_KEY, listener)
        }
    }
}