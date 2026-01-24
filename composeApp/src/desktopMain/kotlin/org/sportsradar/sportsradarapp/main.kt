package org.sportsradar.sportsradarapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.context.startKoin
import org.sportsradar.sportsradarapp.di.configureModules

fun main() = application {
    startKoin {
        configureModules()
    }
    Window(
        onCloseRequest = ::exitApplication,
        title = "SportsRadarApp",
    ) {
        App()
    }
}