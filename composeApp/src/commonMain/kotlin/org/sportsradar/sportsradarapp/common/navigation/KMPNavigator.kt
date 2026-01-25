package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.RetainedEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.backhandler.BackHandler
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavUri
import androidx.navigation.navDeepLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.sportsradar.sportsradarapp.common.presentation.ActivityFinisher
import org.sportsradar.sportsradarapp.common.presentation.rememberActivityFinisher

val LocalKmpNavigator = staticCompositionLocalOf<KMPNavigator> {
    error("No KMPNavigator provided")
}

interface KMPNavigator {
    val currentEntryFlow: Flow<NavBackStackEntry?>

    val tabHistory: TabHistory

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

    companion object {
        val PreviewNavigator = object : KMPNavigator {
            override fun goBack() = Unit
            override fun goTo(screen: Screens) = Unit
            override fun replace(screen: Screens) = Unit
            override fun replaceAll(screen: Screens) = Unit
            override fun popUntil(screen: Screens) = Unit
            override fun goToTab(tab: BottomBarTab) = Unit
            override val currentEntryFlow: Flow<NavBackStackEntry?> = emptyFlow()
            override val tabHistory: TabHistory = TabHistory()
        }
    }
}

@Composable
internal fun rememberKmpNavigator(
    navController: NavController,
): KMPNavigator {
    val tabHistory = rememberTabHistory()
    return remember(navController, tabHistory) {
        KMPNavigatorImpl(navController, tabHistory)
    }
}

fun String.toNavDeeplink(): NavDeepLink = navDeepLink {
    uriPattern = this@toNavDeeplink
}

fun String.toNavUri(): NavUri = NavUri(this)

internal class KMPNavigatorImpl(
    val navController: NavController,
    override val tabHistory: TabHistory,
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
                val tab = ScreensMeta.getByScreen(screen)?.tab
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
        val route = navController.currentBackStackEntry?.screenRoute ?: return false
        val screenName = screens::class.qualifiedName ?: return false
        return screenName == route
    }
}

internal fun NavBackStackEntry?.currentTab(): BottomBarTab? {
    val entry = this ?: return null
    val meta = ScreensMeta.getByEntry(entry) ?: return null
    println("Resolved meta: $meta")
    println("Resolved tab: ${meta.tab}")
    return meta.tab
}

internal inline val NavBackStackEntry.screenRoute: String?
    get() = destination.route?.substringBefore('?')

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun TabRootScreenBackHandler(
    tab: BottomBarTab,
) {
    val navigator = LocalKmpNavigator.current
    val activity = rememberActivityFinisher()
    RetainedEffect(Unit) {
        navigator.tabHistory.pushIfNotLast(tab)
        onRetire {}
    }

    BackHandler {
        navigator.handleBackOnTabRoot(activity)
    }
}

internal fun KMPNavigator.handleBackOnTabRoot(
    activity: ActivityFinisher,
) {
    val previousTab = tabHistory.popPrevious()
    if (previousTab != null) {
        (this as? KMPNavigatorImpl)
            ?.navController
            ?.navigate(previousTab.screen) {
                restoreState = true
            }
    } else {
        activity.finish()
    }
}

