package org.sportsradar.sportsradarapp.common.presentation.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState

expect fun Modifier.tintHazeChild(
    hazeState: HazeState,
    color: Color
): Modifier