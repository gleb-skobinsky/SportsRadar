package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
fun SnackbarScaffold(
    snackbarState: SnackbarHostState,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalSportsRadarTheme.colors.surface),
        snackbarHost = {
            SportsRadarAppSnackbar(snackbarState)
        },
        bottomBar = bottomBar,
        content = content
    )
}