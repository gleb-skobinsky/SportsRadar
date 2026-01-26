package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import org.sportsradar.sportsradarapp.common.presentation.ActivityFinisher
import org.sportsradar.sportsradarapp.common.presentation.rememberActivityFinisher

@Composable
internal fun rememberKmpNavigator(
    navController: NavController,
): KMPNavigator {
    val tabHistory = rememberTabHistory()
    val activityFinisher = rememberActivityFinisher()
    return remember(navController, tabHistory) {
        KMPNavigatorImpl(
            navController = navController,
            tabHistory = tabHistory,
            activityFinisher = activityFinisher
        )
    }
}

private class KMPNavigatorImpl(
    private val navController: NavController,
    private val tabHistory: TabHistory,
    private val activityFinisher: ActivityFinisher,
) : KMPNavigator, TabHistory by tabHistory {

    override val currentEntryFlow = navController.currentBackStackEntryFlow

    override val currentEntry: NavBackStackEntry?
        get() = navController.currentBackStackEntry


    override fun goBack() {
        runSafely {
            val isTabRoot = currentEntry.isTabRoot()
            if (isTabRoot) {
                handleBackOnTabRoot()
            } else {
                navController.navigateUp()
            }
        }
    }

    override fun popUntil(screen: Screens) {
        runSafely {
            navController.popBackStack(
                route = screen,
                inclusive = false
            )
        }
    }

    override fun goTo(screen: Screens) {
        if (hasScreen(screen)) return
        runSafely {
            navController.navigate(screen) {
                launchSingleTop = true
            }
        }
    }

    override fun replace(screen: Screens) {
        if (hasScreen(screen)) return
        runSafely {
            navController.popBackStack()
            navController.navigate(screen)
        }
    }

    override fun replaceAll(screen: Screens) {
        runSafely {
            navController.navigate(screen) {
                val tab = ScreensMeta.getByScreen(screen)?.tab
                if (tab != null) {
                    popUpTo(tab.screen) {
                        inclusive = true
                    }
                }
            }
        }
    }

    private fun handleBackOnTabRoot() {
        val previousTab = tabHistory.popPrevious()
        if (previousTab != null) {
            navController.navigate(previousTab.screen) {
                restoreState = true
            }
        } else {
            activityFinisher.finish()
        }
    }

    override fun goToTab(tab: BottomBarTab) {
        runSafely {
            val prevTab = navController.currentBackStackEntry.currentTab()
            if (prevTab == tab) {
                navController.popBackStack(route = prevTab.screen, inclusive = false)
            } else {
                navController.navigate(tab.screen) {
                    restoreState = true

                    if (prevTab != null) {
                        popUpTo(prevTab.screen) {
                            saveState = true
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    override fun handleDeeplink(deeplink: String) {
        runSafely {
            navController.handleDeepLink(
                NavDeepLinkRequest(
                    uri = deeplink.toNavUri(),
                    action = null,
                    mimeType = null
                )
            )
        }
    }

    private inline fun runSafely(
        crossinline block: () -> Unit
    ) {
        runCatching {
            block()
        }.onFailure {
            it.printStackTrace()
        }
    }

    private fun hasScreen(screens: Screens): Boolean {
        val route = navController.currentBackStackEntry?.screenRoute ?: return false
        val screenName = screens::class.qualifiedName ?: return false
        return screenName == route
    }
}