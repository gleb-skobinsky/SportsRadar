package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sportsradar.sportsradarapp.common.icons.ArrowDown

@Composable
fun PullToRefreshIndicator(
    state: PullToRefreshState,
    refreshTriggerDistance: Dp,
    refreshingOffset: Dp,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle.Default.copy(color = Color.Gray, fontSize = 14.sp),
) {
    val refreshTriggerPx = with(LocalDensity.current) { refreshTriggerDistance.toPx() }
    val refreshingOffsetPx = with(LocalDensity.current) { refreshingOffset.toPx() }
    val indicatorHeight = 48.dp
    val indicatorHeightPx = with(LocalDensity.current) { indicatorHeight.toPx() }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(indicatorHeight)
            .padding(end = 26.dp)
            .graphicsLayer {
                translationY = state.contentOffset - (refreshingOffsetPx + indicatorHeightPx) / 2
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val progress = ((state.contentOffset - refreshTriggerPx / 2) / refreshTriggerPx * 2)
            .coerceIn(0f, 1f)
        Image(
            imageVector = ArrowDown,
            contentDescription = "pull to refresh",
            modifier = Modifier
                .padding(end = 8.dp)
                .size(18.dp)
                .rotate(progress * 180),
            colorFilter = ColorFilter.tint(textStyle.color)
        )
        BasicText(
            text =
            when {
                state.isPullInProgress && state.contentOffset >= refreshTriggerPx -> "Release to refresh"
                else -> "Pull to refresh"
            },
            style = textStyle
        )
    }
}