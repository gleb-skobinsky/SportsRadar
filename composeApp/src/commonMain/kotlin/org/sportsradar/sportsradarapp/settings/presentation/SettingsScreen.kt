package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.stringResource
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.settings
import org.sportsradar.uiKit.components.SportsRadarTopBar

@Composable
internal fun SettingsScreen() {
    SettingsScreenContent(
        navigator = LocalKmpNavigator.current
    )
}

@Composable
private fun SettingsScreenContent(
    navigator: KMPNavigator,
) {
    SportsRadarScaffold(
        topBar = {
            SportsRadarTopBar(
                title = stringResource(AppRes.string.settings),
                onBackClick = navigator::goBack
            )
        }
    ) {

    }
}