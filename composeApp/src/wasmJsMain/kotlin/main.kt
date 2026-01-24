import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import org.koin.core.context.startKoin
import org.sportsradar.sportsradarapp.App
import org.sportsradar.sportsradarapp.di.configureModules

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        configureModules()
    }
    CanvasBasedWindow(canvasElementId = "ComposeTarget") { App() }
}