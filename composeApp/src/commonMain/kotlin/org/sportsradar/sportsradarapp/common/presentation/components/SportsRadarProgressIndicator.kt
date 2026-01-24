package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

private data class OffsetFraction(val xFraction: Float, val yFraction: Float)

private val twinklePositions = listOf(
    OffsetFraction(.5f, .16f),
    OffsetFraction(.74f, .26f),
    OffsetFraction(.83f, .5f),
    OffsetFraction(.74f, .74f),
    OffsetFraction(.5f, .83f),
    OffsetFraction(.26f, .74f),
    OffsetFraction(.16f, .5f),
    OffsetFraction(.26f, .26f)
)

private const val ProgressDuration = 500
private const val SingleTwinkleDuration = ProgressDuration / 8

val DefaultTwinkleSize = 24.dp

@Composable
fun SportsRadarAppTwinklingProgress(
    tint: Color,
    dpSize: Dp = DefaultTwinkleSize,
    modifier: Modifier = Modifier,
) {
    var twinklePosition by remember { mutableIntStateOf(0) }
    LaunchedEffect(Unit) {
        while (isActive) {
            for (pos in (0..7)) {
                delay(SingleTwinkleDuration.toLong())
                twinklePosition = pos
            }
        }
    }
    SportsRadarAppProgressContent(
        dpSize = dpSize,
        twinklePosition = twinklePosition,
        tint = tint,
        modifier = modifier
    )
}

@Composable
internal fun SportsRadarAppProgressContent(
    twinklePosition: Int,
    tint: Color,
    modifier: Modifier = Modifier,
    dpSize: Dp = DefaultTwinkleSize
) {
    val twinkleRadius = dpSize / 12
    Box(
        modifier
            .size(dpSize)
            .drawBehind {
                for ((index, offsetFraction) in twinklePositions.withIndex()) {
                    val alphaRatio = distanceFromPrimary(twinklePosition, index)
                    val (xFraction, yFraction) = offsetFraction
                    drawCircle(
                        color = tint.copy(alpha = 1f / alphaRatio),
                        radius = twinkleRadius.toPx(),
                        center = Offset(x = size.width * xFraction, y = size.height * yFraction)
                    )
                }
            }
    )
}

private fun distanceFromPrimary(twinkleIndex: Int, currentIndex: Int): Int {
    return if (twinkleIndex >= currentIndex) {
        twinkleIndex - currentIndex
    } else {
        val directDistance = twinkleIndex - currentIndex
        8 + directDistance
    }
}
