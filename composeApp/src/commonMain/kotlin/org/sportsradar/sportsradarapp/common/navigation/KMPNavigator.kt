package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.savedstate.read
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

val LocalKmpNavigator = staticCompositionLocalOf<KMPNavigator> {
    error("No KMPNavigator provided")
}

interface KMPNavigator {

    /**
     * Safely navigates back on back button click.
     */
    fun goBack()

    /**
     * Safely navigates to the given screen.
     */
    fun goTo(screen: Screens)

    fun replace(screen: Screens)

    fun replaceAll(screen: Screens)

    fun popUntil(screen: Screens)

    fun goToTab(tab: BottomBarTab)

    val currentEntryFlow: Flow<NavBackStackEntry?>

    companion object {
        val PreviewNavigator = object : KMPNavigator {
            override fun goBack() = Unit
            override fun goTo(screen: Screens) = Unit
            override fun replace(screen: Screens) = Unit
            override fun replaceAll(screen: Screens) = Unit
            override fun popUntil(screen: Screens) = Unit
            override fun goToTab(tab: BottomBarTab) = Unit
            override val currentEntryFlow: Flow<NavBackStackEntry?> = emptyFlow()
        }
    }
}

internal class KMPNavigatorImpl(
    val navController: NavController
) : KMPNavigator {

    override val currentEntryFlow = navController.currentBackStackEntryFlow


    override fun goBack() {
        runSafely {
            navController.navigateUp()
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
                val tab = screen.tab
                if (tab != null) {
                    popUpTo(tab.screen) {
                        inclusive = true
                    }
                }
            }
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
        val route = navController.currentBackStackEntry?.destination?.route ?: return false
        val screenName = screens::class.qualifiedName ?: return false
        return screenName in route
    }
}

internal fun NavBackStackEntry?.currentTab(): BottomBarTab? {
    val entry = this ?: return null
    val tabRoute = entry.destination.rootTabGraph()?.startDestinationRoute
    return BottomBarTab.entries.find {
        it.screen::class.qualifiedName == tabRoute
    }
}

fun NavDestination.rootTabGraph(): NavGraph? {
    var current = this.parent

    while (current?.parent?.parent != null) {
        current = current.parent
    }

    return current
}

