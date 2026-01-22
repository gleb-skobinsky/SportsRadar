package org.sportsradar.uiKit.utils

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.hypot
import kotlin.math.sin

fun Modifier.angledGradient(
    colors: List<Color>,
    angle: Float
) = this.drawBehind {
    drawAngled(angle, colors)
}

fun Modifier.diagonalGradient(
    colors: List<Color>
) = this.drawBehind {
    val angle = atan(size.width / size.height).toDegrees()
    drawAngled(angle, colors)
}

private fun DrawScope.drawAngled(
    degrees: Float,
    colors: List<Color>
) {
    val (x, y) = size
    val gamma = atan2(y, x)

    if (gamma == 0f || gamma == (PI / 2).toFloat()) {
        // degenerate rectangle
        return
    }

    val degreesNormalised = (degrees % 360).let { if (it < 0) it + 360 else it }

    val alpha = (degreesNormalised * PI / 180).toFloat()

    val gradientLength = when (alpha) {
        // ray from centre cuts the right edge of the rectangle
        in 0f..gamma, in (2 * PI - gamma)..2 * PI -> {
            x / cos(alpha)
        }
        // ray from centre cuts the top edge of the rectangle
        in gamma..(PI - gamma).toFloat() -> {
            y / sin(alpha)
        }
        // ray from centre cuts the left edge of the rectangle
        in (PI - gamma)..(PI + gamma) -> {
            x / -cos(alpha)
        }
        // ray from centre cuts the bottom edge of the rectangle
        in (PI + gamma)..(2 * PI - gamma) -> {
            y / -sin(alpha)
        }
        // default case (which shouldn't really happen)
        else -> hypot(x, y)
    }

    val centerOffsetX = cos(alpha) * gradientLength / 2
    val centerOffsetY = sin(alpha) * gradientLength / 2

    drawRect(
        brush = Brush.linearGradient(
            colors = colors,
            start = Offset(center.x - centerOffsetX, center.y - centerOffsetY),
            end = Offset(center.x + centerOffsetX, center.y + centerOffsetY)
        ),
        size = size
    )
}

private fun Float.toDegrees(): Float = (this * 180 / PI).toFloat()