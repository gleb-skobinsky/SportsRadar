package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import dev.chrisbanes.haze.HazeState

@Composable
internal fun rememberHazeState(): HazeState {
    return remember(LocalScreenSize.current) { HazeState() }
}