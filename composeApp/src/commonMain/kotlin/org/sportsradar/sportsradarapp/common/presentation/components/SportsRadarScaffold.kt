package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import org.sportsradar.sportsradarapp.GlobalScaffoldPadding
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
fun SportsRadarScaffold(
    modifier: Modifier = Modifier,
    horizontalPadding: Dp = 32.dp,
    topBarHeight: Dp = 52.dp,
    alignment: Alignment.Horizontal = Alignment.Start,
    arrangement: Arrangement.Vertical = Arrangement.Top,
    topBar: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val scrollState = rememberScrollState()
    val hazeState = remember { HazeState() }
    Scaffold(
        contentColor = LocalSportsRadarTheme.colors.secondary,
        containerColor = LocalSportsRadarTheme.colors.surface,
        topBar = {
            topBar?.let { topBarContent ->
                Box(
                    Modifier
                        .clip(TopBarShape)
                        .maybeApplyHazeEffect(
                            hazeState = hazeState,
                            canScrollBackward = scrollState.canScrollBackward,
                            color = LocalSportsRadarTheme.colors.surfaceTint
                        )
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .height(topBarHeight)
                ) {
                    topBarContent()
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .hazeSource(hazeState)
                .background(LocalSportsRadarTheme.colors.surface)
                .fillMaxSize()
                .imePadding()
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

private val TopBarShape = RoundedCornerShape(
    bottomStart = 20.dp,
    bottomEnd = 20.dp
)

@Stable
private fun Modifier.maybeApplyHazeEffect(
    canScrollBackward: Boolean,
    color: Color,
    hazeState: HazeState?,
): Modifier {
    return if (canScrollBackward) {
        this.hazeEffect(
            state = hazeState,
            style = HazeDefaults.style(color)
        )
    } else {
        this
    }
}
