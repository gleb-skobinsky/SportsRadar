package org.sportsradar.sportsradarapp.common.presentation.components

import GlobalScaffoldPadding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.hazeEffect
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
fun SportsRadarScaffold(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 32.dp,
    alignment: Alignment.Horizontal = Alignment.Start,
    arrangement: Arrangement.Vertical = Arrangement.Top,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()
    val canScrollBackward = scrollState.canScrollBackward
    Scaffold(
        topBar = {
            topBar?.let { topBarContent ->
                Box(Modifier.hazeEffect())
                if (scrollState.canScrollForward) {

                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .background(LocalSportsRadarTheme.colors.surface)
                .fillMaxSize()
                .imePadding()
                .statusBarsPadding()
                .verticalScroll(scrollState)
                .padding(GlobalScaffoldPadding.current)
                .padding(paddingValues)
                .padding(horizontal = horizontalPadding),
            horizontalAlignment = alignment,
            verticalArrangement = arrangement,
            content = content
        )
    }
}