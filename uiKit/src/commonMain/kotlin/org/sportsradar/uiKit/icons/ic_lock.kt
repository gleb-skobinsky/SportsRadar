package org.sportsradar.uiKit.icons

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Lock: ImageVector
    get() {
        if (_Lock != null) {
            return _Lock!!
        }
        _Lock = ImageVector.Builder(
            name = "Lock",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(244, 128, 102),
                        0.5f to Color(245, 149, 129),
                        1f to Color(245, 177, 164)
                    ),
                    start = Offset(5.25f, 1.25f),
                    end = Offset(12.563f, 13.343f)
                )
            ) {
                moveTo(18f, 10.75f)
                curveTo(17.59f, 10.75f, 17.25f, 10.41f, 17.25f, 10f)
                verticalLineTo(8f)
                curveTo(17.25f, 4.85f, 16.36f, 2.75f, 12f, 2.75f)
                curveTo(7.64f, 2.75f, 6.75f, 4.85f, 6.75f, 8f)
                verticalLineTo(10f)
                curveTo(6.75f, 10.41f, 6.41f, 10.75f, 6f, 10.75f)
                curveTo(5.59f, 10.75f, 5.25f, 10.41f, 5.25f, 10f)
                verticalLineTo(8f)
                curveTo(5.25f, 5.1f, 5.95f, 1.25f, 12f, 1.25f)
                curveTo(18.05f, 1.25f, 18.75f, 5.1f, 18.75f, 8f)
                verticalLineTo(10f)
                curveTo(18.75f, 10.41f, 18.41f, 10.75f, 18f, 10.75f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(244, 128, 102),
                        0.5f to Color(245, 149, 129),
                        1f to Color(245, 177, 164)
                    ),
                    start = Offset(8.75f, 12.75f),
                    end = Offset(14.336f, 19.25f)
                )
            ) {
                moveTo(12f, 19.25f)
                curveTo(10.21f, 19.25f, 8.75f, 17.79f, 8.75f, 16f)
                curveTo(8.75f, 14.21f, 10.21f, 12.75f, 12f, 12.75f)
                curveTo(13.79f, 12.75f, 15.25f, 14.21f, 15.25f, 16f)
                curveTo(15.25f, 17.79f, 13.79f, 19.25f, 12f, 19.25f)
                close()
                moveTo(12f, 14.25f)
                curveTo(11.04f, 14.25f, 10.25f, 15.04f, 10.25f, 16f)
                curveTo(10.25f, 16.96f, 11.04f, 17.75f, 12f, 17.75f)
                curveTo(12.96f, 17.75f, 13.75f, 16.96f, 13.75f, 16f)
                curveTo(13.75f, 15.04f, 12.96f, 14.25f, 12f, 14.25f)
                close()
            }
            path(
                fill = Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to Color(244, 128, 102),
                        0.5f to Color(245, 149, 129),
                        1f to Color(245, 177, 164)
                    ),
                    start = Offset(1.25f, 9.25f),
                    end = Offset(11.059f, 27.427f)
                )
            ) {
                moveTo(17f, 22.75f)
                horizontalLineTo(7f)
                curveTo(2.59f, 22.75f, 1.25f, 21.41f, 1.25f, 17f)
                verticalLineTo(15f)
                curveTo(1.25f, 10.59f, 2.59f, 9.25f, 7f, 9.25f)
                horizontalLineTo(17f)
                curveTo(21.41f, 9.25f, 22.75f, 10.59f, 22.75f, 15f)
                verticalLineTo(17f)
                curveTo(22.75f, 21.41f, 21.41f, 22.75f, 17f, 22.75f)
                close()
                moveTo(7f, 10.75f)
                curveTo(3.42f, 10.75f, 2.75f, 11.43f, 2.75f, 15f)
                verticalLineTo(17f)
                curveTo(2.75f, 20.57f, 3.42f, 21.25f, 7f, 21.25f)
                horizontalLineTo(17f)
                curveTo(20.58f, 21.25f, 21.25f, 20.57f, 21.25f, 17f)
                verticalLineTo(15f)
                curveTo(21.25f, 11.43f, 20.58f, 10.75f, 17f, 10.75f)
                horizontalLineTo(7f)
                close()
            }
        }.build()

        return _Lock!!
    }

@Suppress("ObjectPropertyName")
private var _Lock: ImageVector? = null
