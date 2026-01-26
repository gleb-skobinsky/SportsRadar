package org.sportsradar.sportsradarapp

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestinationDsl
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sportsradar.sportsradarapp.auth.presentation.forgotPasswordScreen.ForgotPasswordScreen
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.LoginScreen
import org.sportsradar.sportsradarapp.auth.presentation.profileScreen.ProfileScreen
import org.sportsradar.sportsradarapp.auth.presentation.signupScreen.SignupScreen
import org.sportsradar.sportsradarapp.common.navigation.BottomBarTab
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.navigation.ScreensMeta
import org.sportsradar.sportsradarapp.common.navigation.TabRootScreenBackHandler
import org.sportsradar.sportsradarapp.common.navigation.rememberKmpNavigator
import org.sportsradar.sportsradarapp.common.presentation.LocalScreenSize
import org.sportsradar.sportsradarapp.common.presentation.RootSnackbarController
import org.sportsradar.sportsradarapp.common.presentation.components.SnackbarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppNavBarWrapper
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.handleWebDeepLinkOnStart
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import org.sportsradar.uiKit.theme.SportsRadarTheme

private const val FAST_NAV_ANIMATION = 300

@PublishedApi
internal val GlobalScaffoldPadding = compositionLocalOf {
    PaddingValues(0.dp)
}

// TODO: Move to build config
private const val MAIN_HOST = "http://localhost:3000"

@Composable
@Preview
@NonRestartableComposable
fun App() {
    SportsRadarTheme {
        val navController = rememberNavController()
        val navigator = rememberKmpNavigator(navController)

        val haze = rememberHazeState()

        navigator.handleWebDeepLinkOnStart()

        CompositionLocalProvider(
            LocalKmpNavigator provides navigator,
        ) {
            SnackbarScaffold(
                snackbarState = RootSnackbarController.snackbarState,
                bottomBar = {
                    SportsRadarAppNavBarWrapper(haze)
                }
            ) { paddingValues ->
                CompositionLocalProvider(GlobalScaffoldPadding provides paddingValues) {
                    NavHost(
                        navController = navController,
                        modifier = Modifier
                            .fillMaxSize()
                            .hazeSource(haze)
                            .background(LocalSportsRadarTheme.colors.surface),
                        contentAlignment = Alignment.Center,
                        enterTransition = { fadeIn(tween(FAST_NAV_ANIMATION)) },
                        exitTransition = { fadeOut(tween(FAST_NAV_ANIMATION)) },
                        startDestination = Screens.HomeTabScreen
                    ) {
                        screensNavigation<Screens.HomeTabScreen>(
                            startDestination = Screens.HomeScreen,
                        ) {
                            screensComposable<Screens.HomeScreen> {
                                TabRootScreenBackHandler(BottomBarTab.HomeTab)
                                SportsRadarScaffold {}
                            }
                        }
                        screensNavigation<Screens.ProfileTabScreen>(
                            startDestination = Screens.ProfileScreen,
                        ) {
                            screensComposable<Screens.ProfileScreen> {
                                TabRootScreenBackHandler(BottomBarTab.ProfileTab)
                                ProfileScreen()
                            }
                            screensNavigation<Screens.AuthGraph>(
                                startDestination = Screens.LoginScreen
                            ) {
                                screensComposable<Screens.LoginScreen> { LoginScreen() }
                                screensComposable<Screens.SignupScreen> { SignupScreen() }
                                screensComposable<Screens.ForgotPasswordScreen> {
                                    val route = it.toRoute<Screens.ForgotPasswordScreen>()
                                    ForgotPasswordScreen(route.email)
                                }
                            }
                        }
                        screensNavigation<Screens.FavoritesTabScreen>(
                            startDestination = Screens.FavoritesScreen,
                        ) {
                            screensComposable<Screens.FavoritesScreen> {
                                TabRootScreenBackHandler(BottomBarTab.FavoritesTab)
                                SportsRadarScaffold {}
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberHazeState(): HazeState {
    return remember(LocalScreenSize.current) { HazeState() }
}

@NavDestinationDsl
private inline fun <reified S : Screens> NavGraphBuilder.screensNavigation(
    startDestination: Any,
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    navigation<S>(
        startDestination = startDestination,
        builder = builder
    )
}

@NavDestinationDsl
private inline fun <reified S : Screens> NavGraphBuilder.screensComposable(
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    val meta = ScreensMeta.getByKClass(S::class)
    composable<S>(
        deepLinks = listOfNotNull(meta?.navDeeplink(MAIN_HOST)),
        content = content
    )
}
