package org.sportsradar.sportsradarapp.common.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val Favorites: ImageVector
    get() {
        if (_Shop != null) {
            return _Shop!!
        }
        _Shop = ImageVector.Builder(
            name = "Shop",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(7.52f, 17f)
                curveTo(6.621f, 17f, 5.77f, 16.799f, 5.013f, 16.418f)
                verticalLineTo(20.947f)
                curveTo(5.013f, 26.148f, 6.772f, 28.147f, 11.667f, 28.321f)
                verticalLineTo(26f)
                curveTo(11.667f, 23.2f, 13.2f, 21.667f, 16f, 21.667f)
                curveTo(18.8f, 21.667f, 20.333f, 23.2f, 20.333f, 26f)
                verticalLineTo(28.32f)
                curveTo(25.218f, 28.143f, 26.973f, 26.143f, 26.973f, 20.947f)
                verticalLineTo(16.391f)
                curveTo(26.204f, 16.789f, 25.333f, 17f, 24.413f, 17f)
                curveTo(22.742f, 17f, 21.182f, 16.166f, 20.168f, 14.894f)
                curveTo(20.052f, 15.06f, 19.924f, 15.22f, 19.787f, 15.373f)
                curveTo(18.8f, 16.427f, 17.467f, 17f, 16f, 17f)
                curveTo(14.533f, 17f, 13.2f, 16.427f, 12.253f, 15.373f)
                curveTo(12.096f, 15.198f, 11.952f, 15.013f, 11.823f, 14.821f)
                curveTo(10.81f, 16.134f, 9.223f, 17f, 7.52f, 17f)
                close()
                moveTo(19.333f, 30.333f)
                lineTo(19.6f, 30.333f)
                curveTo(26.173f, 30.333f, 28.987f, 27.52f, 28.973f, 20.947f)
                verticalLineTo(14.96f)
                curveTo(28.973f, 14.855f, 28.956f, 14.753f, 28.926f, 14.657f)
                curveTo(29.633f, 13.617f, 29.947f, 12.356f, 29.813f, 11.04f)
                lineTo(29.44f, 7.333f)
                curveTo(28.893f, 3.306f, 27.067f, 1.666f, 23.147f, 1.666f)
                horizontalLineTo(19.133f)
                horizontalLineTo(19.08f)
                horizontalLineTo(12.907f)
                curveTo(12.898f, 1.666f, 12.889f, 1.667f, 12.88f, 1.667f)
                curveTo(12.871f, 1.667f, 12.862f, 1.666f, 12.853f, 1.666f)
                horizontalLineTo(8.787f)
                curveTo(4.867f, 1.666f, 3.04f, 3.306f, 2.48f, 7.373f)
                lineTo(2.12f, 11.04f)
                curveTo(1.984f, 12.379f, 2.311f, 13.661f, 3.045f, 14.711f)
                curveTo(3.024f, 14.791f, 3.013f, 14.874f, 3.013f, 14.96f)
                verticalLineTo(20.947f)
                curveTo(3.013f, 27.52f, 5.813f, 30.333f, 12.4f, 30.333f)
                lineTo(12.667f, 30.333f)
                horizontalLineTo(19.333f)
                close()
                moveTo(18.333f, 28.333f)
                verticalLineTo(26f)
                curveTo(18.333f, 24.32f, 17.68f, 23.667f, 16f, 23.667f)
                curveTo(14.32f, 23.667f, 13.667f, 24.32f, 13.667f, 26f)
                verticalLineTo(28.333f)
                horizontalLineTo(18.333f)
                close()
                moveTo(8.787f, 3.667f)
                curveTo(5.867f, 3.667f, 4.893f, 4.547f, 4.467f, 7.6f)
                lineTo(4.107f, 11.24f)
                curveTo(4f, 12.253f, 4.307f, 13.2f, 4.947f, 13.906f)
                curveTo(5.587f, 14.613f, 6.493f, 15f, 7.52f, 15f)
                curveTo(9.2f, 15f, 10.76f, 13.587f, 10.92f, 11.92f)
                lineTo(11.747f, 3.667f)
                horizontalLineTo(8.787f)
                close()
                moveTo(19.016f, 12.145f)
                curveTo(18.989f, 12.852f, 18.727f, 13.511f, 18.267f, 14.026f)
                curveTo(17.133f, 15.28f, 14.867f, 15.28f, 13.733f, 14.026f)
                curveTo(13.173f, 13.413f, 12.907f, 12.573f, 13f, 11.68f)
                lineTo(13.8f, 3.667f)
                horizontalLineTo(18.169f)
                lineTo(19.013f, 12.12f)
                lineTo(19.016f, 12.145f)
                close()
                moveTo(21.052f, 12.182f)
                curveTo(21.351f, 13.732f, 22.822f, 15f, 24.413f, 15f)
                curveTo(25.427f, 15f, 26.333f, 14.613f, 26.987f, 13.906f)
                curveTo(27.627f, 13.2f, 27.92f, 12.253f, 27.827f, 11.24f)
                lineTo(27.453f, 7.573f)
                curveTo(27.04f, 4.56f, 26.067f, 3.667f, 23.147f, 3.667f)
                horizontalLineTo(20.243f)
                lineTo(21.027f, 11.48f)
                curveTo(21.05f, 11.717f, 21.059f, 11.951f, 21.052f, 12.182f)
                close()
            }
        }.build()

        return _Shop!!
    }

@Suppress("ObjectPropertyName")
private var _Shop: ImageVector? = null
