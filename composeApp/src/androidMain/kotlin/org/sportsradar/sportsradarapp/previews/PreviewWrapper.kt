package org.sportsradar.sportsradarapp.previews

import ProvideNavigator
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import org.sportsradar.uiKit.theme.SportsRadarTheme
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigatorImpl

@Composable
internal fun PreviewWrapper(
    content: @Composable () -> Unit
) {
    SportsRadarTheme {
        ProvideNavigator(KMPNavigatorImpl(rememberNavController())) {
            content()
        }
    }
}