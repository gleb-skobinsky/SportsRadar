package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.animation.core.tween
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.themeanimator.ThemeAnimationFormat
import io.github.themeanimator.ThemeAnimationScope
import io.github.themeanimator.ThemeAnimationState
import io.github.themeanimator.button.ThemeSwitchButton
import io.github.themeanimator.rememberThemeAnimationState
import io.github.themeanimator.storage.themeViewModel
import org.jetbrains.compose.resources.stringResource
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.settings
import org.sportsradar.sportsradarapp.resources.theme_setting
import org.sportsradar.uiKit.components.SportsRadarTopBar
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
internal fun sportsRadarThemeViewModel() = themeViewModel(
    preferencesFileName = "sportsradar_theme.preferences_pb",
    jvmChildDirectory = ".sportsradar"
)

@Composable
internal fun SettingsScreen() {
    val themeViewModel = sportsRadarThemeViewModel()
    SettingsScreenContent(
        animationState = rememberThemeAnimationState(
            themeProvider = themeViewModel,
            format = ThemeAnimationFormat.CircularAroundPress,
            animationSpec = tween(700)
        ),
        navigator = LocalKmpNavigator.current
    )
}

@Composable
private fun SettingsScreenContent(
    animationState: ThemeAnimationState,
    navigator: KMPNavigator,
) {
    ThemeAnimationScope(
        state = animationState
    ) {
        SportsRadarScaffold(
            topBar = {
                SportsRadarTopBar(
                    title = stringResource(AppRes.string.settings),
                    onBackClick = navigator::goBack
                )
            }
        ) {
            40.dp.VerticalSpacer()
            Text(
                text = stringResource(AppRes.string.theme_setting),
                style = LocalSportsRadarTheme.typography.bodySmall,
                color = LocalSportsRadarTheme.colors.secondary
            )
            ThemeSwitchButton(
                animationState = animationState,
                iconTint = LocalSportsRadarTheme.colors.secondary
            )
        }
    }
}