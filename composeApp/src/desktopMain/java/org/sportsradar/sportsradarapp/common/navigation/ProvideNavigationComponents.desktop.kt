package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable

@Composable
internal actual fun ProvideNavigationComponents(
    navigator: KMPNavigator,
    content: @Composable (() -> Unit)
) = ProvideCommonNavigation(navigator, content)