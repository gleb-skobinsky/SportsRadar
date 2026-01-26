package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import kotlinx.browser.window
import org.sportsradar.sportsradarapp.common.presentation.TabHistorySaver
import org.w3c.dom.events.Event

private const val BEFORE_UNLOAD_KEY = "beforeunload"

@Composable
internal inline fun RegisterBeforeUnloadEffect(
    crossinline beforeUnload: () -> Unit
) {
    DisposableEffect(Unit) {
        val listener: (Event) -> Unit = {
            beforeUnload()
        }

        window.addEventListener(BEFORE_UNLOAD_KEY, listener)

        onDispose {
            window.removeEventListener(BEFORE_UNLOAD_KEY, listener)
        }
    }
}

@Composable
actual fun rememberTabHistory(): TabHistory {
    val saver = remember { TabHistorySaver() }
    val tabHistory = remember {
        TabHistoryImpl().apply {
            saver.restoreTabs().forEach { tab ->
                pushIfNotLast(tab)
            }
        }
    }
    RegisterBeforeUnloadEffect {
        saver.saveTabs(tabHistory.stack.toList())
    }
    return tabHistory
}

