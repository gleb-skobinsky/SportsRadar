package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.unit.DpSize

expect object LocalScreenSize {
    val current: DpSize
        @Composable
        @ReadOnlyComposable
        get
}