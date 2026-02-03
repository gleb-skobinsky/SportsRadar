package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

// TODO: Move to build config
private const val MAIN_HOST = "http://localhost:3000"

@NavDestinationDsl
internal inline fun <reified S : Screens> NavGraphBuilder.screensNavigation(
    startDestination: Any,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    navigation<S>(
        startDestination = startDestination,
        builder = builder
    )
}

@NavDestinationDsl
internal inline fun <reified S : Screens> NavGraphBuilder.screensComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    val meta = ScreensMeta.getByKClass(S::class)
    composable<S>(
        deepLinks = listOfNotNull(meta?.navDeeplink(MAIN_HOST)),
        content = {
            RegisterTabVisitedAndBackHandler(meta?.tab)
            content(it)
        }
    )
}
