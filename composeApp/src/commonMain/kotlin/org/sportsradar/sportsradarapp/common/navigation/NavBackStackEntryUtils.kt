package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.sportsradar.sportsradarapp.common.utils.isCreated
import org.sportsradar.sportsradarapp.common.utils.isResumed
import org.sportsradar.sportsradarapp.common.utils.isStarted
import kotlinx.coroutines.flow.collectLatest

/**
 * This composable utility function captures the state when the back stack entry re-appears on the screen.
 *
 * Simple subscription to the [androidx.lifecycle.Lifecycle.State.STARTED] does not work here,
 * because the entries are recreated on screen rotation (for Android only).
 */
@Composable
fun NavBackStackEntry.OnAppear(controller: NavController, action: () -> Unit) {
    LaunchedEffect(Unit) {
        controller.visibleEntries.collectLatest { entries ->
            val leavingEntry = entries.firstOrNull { it.isCreated }
            val appearingEntry = entries.firstOrNull { it.isStarted || it.isResumed }
            leavingEntry?.let {
                if (appearingEntry == this@OnAppear) action()
            }
        }
    }
}
