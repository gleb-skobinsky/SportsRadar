import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.sportsradar.sportsradarapp.auth.presentation.forgotPasswordScreen.ForgotPasswordScreen
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.LoginScreen
import org.sportsradar.sportsradarapp.auth.presentation.signupScreen.SignupScreen
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigatorImpl
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.presentation.RootSnackbarController
import org.sportsradar.sportsradarapp.common.presentation.components.SnackbarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppNavBarWrapper
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import org.sportsradar.uiKit.theme.SportsRadarTheme

private const val FAST_NAV_ANIMATION = 300

@Composable
@Preview
@NonRestartableComposable
fun App() {
    SportsRadarTheme {
        val navController = rememberNavController()
        val navigator: KMPNavigator = remember { KMPNavigatorImpl(navController) }
        val haze = remember { HazeState() }
        ProvideNavigator(navigator) {
            SnackbarScaffold(
                snackbarState = RootSnackbarController.snackbarState,
                bottomBar = {
                    SportsRadarAppNavBarWrapper(haze)
                }
            ) {
                NavHost(
                    navController = navController,
                    modifier = Modifier
                        .hazeSource(haze)
                        .background(LocalSportsRadarTheme.colors.surface),
                    enterTransition = { fadeIn(tween(FAST_NAV_ANIMATION)) },
                    exitTransition = { fadeOut(tween(FAST_NAV_ANIMATION)) },
                    startDestination = Screens.LoginScreen
                ) {
                    // Auth graph
                    composable<Screens.LoginScreen> { LoginScreen() }
                    composable<Screens.SignupScreen> { SignupScreen() }
                    composable<Screens.ForgotPasswordScreen> {
                        val route = it.toRoute<Screens.ForgotPasswordScreen>()
                        ForgotPasswordScreen(route.email)
                    }
                    composable<Screens.HomeScreen> { }
                    composable<Screens.FeedScreen> { }
                    composable<Screens.ProfileScreen> { }
                }
            }
        }
    }
}

@Composable
fun ProvideNavigator(
    navigator: KMPNavigator,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalKmpNavigator provides navigator,
        content = content
    )
}