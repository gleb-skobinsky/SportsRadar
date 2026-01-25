package org.sportsradar.sportsradarapp.common.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val Home: ImageVector
    get() {
        if (IcHome != null) {
            return IcHome!!
        }
        IcHome = ImageVector.Builder(
            name = "Home",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFFFFFFFF),
                        0.5f to Color(0xFFFFFFFF),
                        1f to Color(0xFFFFFFFF)
                    ),
                    start = Offset(1.664f, 1.9f),
                    end = Offset(25.816f, 30.489f)
                )
            ) {
                moveTo(23.466f, 30.08f)
                horizontalLineTo(8.533f)
                curveTo(6.106f, 30.08f, 3.893f, 28.213f, 3.493f, 25.813f)
                lineTo(1.72f, 15.2f)
                curveTo(1.44f, 13.547f, 2.24f, 11.427f, 3.56f, 10.373f)
                lineTo(12.8f, 2.973f)
                curveTo(14.587f, 1.533f, 17.4f, 1.547f, 19.2f, 2.987f)
                lineTo(28.44f, 10.373f)
                curveTo(29.747f, 11.427f, 30.546f, 13.547f, 30.28f, 15.2f)
                lineTo(28.507f, 25.813f)
                curveTo(28.107f, 28.173f, 25.853f, 30.08f, 23.466f, 30.08f)
                close()
                moveTo(15.986f, 3.92f)
                curveTo(15.28f, 3.92f, 14.573f, 4.133f, 14.053f, 4.547f)
                lineTo(4.813f, 11.947f)
                curveTo(4.053f, 12.56f, 3.533f, 13.92f, 3.693f, 14.88f)
                lineTo(5.466f, 25.493f)
                curveTo(5.706f, 26.893f, 7.106f, 28.08f, 8.533f, 28.08f)
                horizontalLineTo(23.466f)
                curveTo(24.893f, 28.08f, 26.293f, 26.893f, 26.533f, 25.48f)
                lineTo(28.306f, 14.867f)
                curveTo(28.466f, 13.907f, 27.933f, 12.533f, 27.187f, 11.933f)
                lineTo(17.947f, 4.547f)
                curveTo(17.413f, 4.133f, 16.706f, 3.92f, 15.986f, 3.92f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFFFFFFFF),
                        0.5f to Color(0xFFFFFFFF),
                        1f to Color(0xFFFFFFFF)
                    ),
                    start = Offset(11.666f, 13f),
                    end = Offset(19.114f, 21.667f)
                )
            ) {
                moveTo(15.999f, 21.667f)
                curveTo(13.613f, 21.667f, 11.666f, 19.72f, 11.666f, 17.333f)
                curveTo(11.666f, 14.947f, 13.613f, 13f, 15.999f, 13f)
                curveTo(18.386f, 13f, 20.333f, 14.947f, 20.333f, 17.333f)
                curveTo(20.333f, 19.72f, 18.386f, 21.667f, 15.999f, 21.667f)
                close()
                moveTo(15.999f, 15f)
                curveTo(14.719f, 15f, 13.666f, 16.053f, 13.666f, 17.333f)
                curveTo(13.666f, 18.613f, 14.719f, 19.667f, 15.999f, 19.667f)
                curveTo(17.279f, 19.667f, 18.333f, 18.613f, 18.333f, 17.333f)
                curveTo(18.333f, 16.053f, 17.279f, 15f, 15.999f, 15f)
                close()
            }
        }.build()

        return IcHome!!
    }

private var IcHome: ImageVector? = null
