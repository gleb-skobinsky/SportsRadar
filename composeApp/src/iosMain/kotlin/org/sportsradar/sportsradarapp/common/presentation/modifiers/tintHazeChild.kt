package org.sportsradar.sportsradarapp.common.presentation.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.hazeEffect

actual fun Modifier.tintHazeChild(
    hazeState: HazeState,
    color: Color
) = this.hazeEffect(
    state = hazeState,
    style = HazeDefaults.style(backgroundColor = color)
)