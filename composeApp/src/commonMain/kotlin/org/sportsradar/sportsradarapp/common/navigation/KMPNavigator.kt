package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
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

    val currentEntry: Flow<NavBackStackEntry?>

    fun hasScreen(screen: Screens): Boolean

    companion object {
        val PreviewNavigator = object : KMPNavigator {
            override fun goBack() = Unit
            override fun goTo(screen: Screens) = Unit
            override fun replace(screen: Screens) = Unit
            override fun replaceAll(screen: Screens) = Unit
            override fun popUntil(screen: Screens) = Unit
            override val currentEntry: Flow<NavBackStackEntry?> = emptyFlow()
            override fun hasScreen(screen: Screens): Boolean = false
        }
    }
}

class KMPNavigatorImpl(
    private val navController: NavController
) : KMPNavigator {

    override val currentEntry = navController.currentBackStackEntryFlow

    override fun hasScreen(screen: Screens): Boolean =
        navController.currentBackStackEntry.hasScreen(screen)

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
                popUpTo(0) {
                    inclusive = true
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
}

fun NavBackStackEntry?.hasScreen(screen: Screens): Boolean {
    val route = this?.destination?.route ?: return false
    val screenName = screen::class.qualifiedName ?: return false
    return screenName in route
}