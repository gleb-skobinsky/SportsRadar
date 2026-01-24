import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import androidx.compose.ui.window.ComposeViewport
import org.koin.core.context.startKoin
import org.sportsradar.sportsradarapp.App
import org.sportsradar.sportsradarapp.di.configureModules

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    startKoin {
        configureModules()
    }
    ComposeViewport(
        content = { App() }
    )
}