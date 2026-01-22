package org.sportsradar.sportsradarapp

import App
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowInsetsControllerCompat
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.sportsradar.sportsradarapp.init.presentation.InitStateController

class MainActivity : ComponentActivity(), KoinComponent {
    private val initViewModel: InitStateController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                initViewModel.state.value.sessionCheckLoading
            }
        }
        enableEdgeToEdge()

        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = false
            isAppearanceLightNavigationBars = false
        }
        window.navigationBarColor = Color.TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT

        setContent {
            App(initViewModel, savedInstanceState)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

