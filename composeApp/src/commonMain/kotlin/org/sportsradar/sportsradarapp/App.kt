package org.sportsradar.sportsradarapp

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.toRoute
import dev.chrisbanes.haze.hazeSource
import io.github.themeanimator.theme.isDark
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sportsradar.sportsradarapp.auth.presentation.forgotPasswordScreen.ForgotPasswordScreen
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.LoginScreen
import org.sportsradar.sportsradarapp.auth.presentation.signupScreen.SignupScreen
import org.sportsradar.sportsradarapp.common.navigation.ProvideCommonNavigation
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.navigation.rememberController
import org.sportsradar.sportsradarapp.common.navigation.rememberKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.screensComposable
import org.sportsradar.sportsradarapp.common.navigation.screensNavigation
import org.sportsradar.sportsradarapp.common.presentation.GlobalScaffoldPadding
import org.sportsradar.sportsradarapp.common.presentation.RootSnackbarController
import org.sportsradar.sportsradarapp.common.presentation.components.SnackbarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppNavBarWrapper
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.handleWebDeepLinkOnStart
import org.sportsradar.sportsradarapp.common.presentation.rememberHazeState
import org.sportsradar.sportsradarapp.profile.presentation.ProfileScreen
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.favorites_screen_header
import org.sportsradar.sportsradarapp.resources.home_screen_header
import org.sportsradar.sportsradarapp.settings.presentation.SettingsScreen
import org.sportsradar.sportsradarapp.settings.presentation.sportsRadarThemeViewModel
import org.sportsradar.uiKit.components.SportsRadarTopBar
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import org.sportsradar.uiKit.theme.SportsRadarTheme

private const val FAST_NAV_ANIMATION = 300

@Composable
@Preview
@NonRestartableComposable
fun App() {
    val themeViewModel = sportsRadarThemeViewModel()
    val theme by themeViewModel.currentTheme.collectAsState()
    SportsRadarTheme(theme.isDark()) {
        val navController = rememberController()
        val navigator = rememberKmpNavigator(navController)

        val haze = rememberHazeState()

        navigator.handleWebDeepLinkOnStart()

        ProvideCommonNavigation(navigator) {
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
                                SportsRadarScaffold(
                                    topBar = {
                                        SportsRadarTopBar(
                                            title = stringResource(AppRes.string.home_screen_header),
                                            onBackClick = navigator::goBack,
                                        )
                                    }
                                ) {}
                            }
                        }
                        screensNavigation<Screens.ProfileTabScreen>(
                            startDestination = Screens.ProfileScreen,
                        ) {
                            screensComposable<Screens.ProfileScreen> {
                                ProfileScreen()
                            }
                            screensComposable<Screens.SettingsScreen> {
                                SettingsScreen()
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
                                SportsRadarScaffold(
                                    topBar = {
                                        SportsRadarTopBar(
                                            title = stringResource(AppRes.string.favorites_screen_header),
                                            onBackClick = navigator::goBack,
                                        )
                                    }
                                ) {}
                            }
                        }
                    }
                }
            }
        }
    }
}
