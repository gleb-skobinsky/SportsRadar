package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun SportsRadarAppSnackbar(snackbarState: SnackbarHostState) {
    SnackbarHost(snackbarState) { data ->
        Snackbar(
            snackbarData = data,
            shape = RoundedCornerShape(12.dp),
            containerColor = LocalSportsRadarTheme.colors.secondary,
            contentColor = LocalSportsRadarTheme.colors.onSecondaryContainer,
        )
    }
}