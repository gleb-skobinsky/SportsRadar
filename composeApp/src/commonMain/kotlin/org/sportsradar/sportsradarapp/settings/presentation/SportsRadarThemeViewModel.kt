package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.runtime.Composable
import io.github.themeanimator.storage.themeViewModel

@Composable
internal fun sportsRadarThemeViewModel() = themeViewModel(
    preferencesFileName = "sportsradar_theme.preferences_pb",
    jvmChildDirectory = ".sportsradar"
)