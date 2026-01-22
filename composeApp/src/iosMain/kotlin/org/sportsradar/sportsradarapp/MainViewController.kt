package org.sportsradar.sportsradarapp

import App
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.uikit.OnFocusBehavior
import androidx.compose.ui.window.ComposeUIViewController
import org.sportsradar.sportsradarapp.common.presentation.modifiers.noRippleClickable
import platform.UIKit.UIViewController

@Suppress("Unused", "FunctionName")
fun MainViewController(): UIViewController = ComposeUIViewController(
    configure = {
        onFocusBehavior = OnFocusBehavior.DoNothing
    }
) {
    val focusManager = LocalFocusManager.current
    Box(
        modifier = Modifier.noRippleClickable {
            focusManager.clearFocus(true)
        },
        content = { App() }
    )
}
