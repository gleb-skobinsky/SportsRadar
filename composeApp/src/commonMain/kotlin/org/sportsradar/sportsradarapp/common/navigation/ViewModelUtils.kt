package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import org.koin.compose.viewmodel.koinViewModel

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavHostController,
): VM {
    val parentEntry = remember(this) {
        runCatching {
            destination.parent?.route
                ?.let { navController.getBackStackEntry(it) }
                ?: this
        }.getOrNull() ?: this
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}

@Composable
inline fun <reified VM : ViewModel> NavBackStackEntry.koinViewModel(): VM =
    koinViewModel(viewModelStoreOwner = this)
