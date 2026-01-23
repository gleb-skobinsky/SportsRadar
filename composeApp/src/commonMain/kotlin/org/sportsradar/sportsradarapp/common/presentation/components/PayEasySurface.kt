package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
inline fun SportsRadarAppSurface(
    modifier: Modifier = Modifier,
    scrollable: Boolean = true,
    horizontalPadding: Dp = 32.dp,
    handleStatusBars: Boolean = true,
    alignment: Alignment.Horizontal = Alignment.Start,
    arrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable ColumnScope.() -> Unit
) {
    val modifierInternal =
        if (scrollable) Modifier.verticalScroll(rememberScrollState()) else Modifier
    val paddingsModifier = if (handleStatusBars) Modifier.statusBarsPadding() else Modifier
    Column(
        modifier = modifier
            .background(LocalSportsRadarTheme.colors.surface)
            .fillMaxSize()
            .imePadding()
            .then(paddingsModifier)
            .then(modifierInternal)
            .padding(horizontal = horizontalPadding),
        horizontalAlignment = alignment,
        verticalArrangement = arrangement,
        content = content
    )
}