package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
internal expect fun ProvideNavigationComponents(
    navigator: KMPNavigator,
    content: @Composable () -> Unit,
)

@Composable
internal fun ProvideCommonNavigation(
    navigator: KMPNavigator,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(
        value = LocalKmpNavigator provides navigator,
        content = content
    )
}