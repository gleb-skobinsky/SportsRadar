package org.sportsradar.uiKit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import kotlin.Suppress

val ArrowRight: ImageVector
    get() {
        if (_ArrowRight != null) {
            return _ArrowRight!!
        }
        _ArrowRight = ImageVector.Builder(
            name = "ArrowRight",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(fill = SolidColor(Color(0xFFAAAAAA))) {
                moveTo(14.429f, 18.82f)
                curveTo(14.239f, 18.82f, 14.049f, 18.75f, 13.899f, 18.6f)
                curveTo(13.609f, 18.31f, 13.609f, 17.83f, 13.899f, 17.54f)
                lineTo(19.439f, 12f)
                lineTo(13.899f, 6.46f)
                curveTo(13.609f, 6.17f, 13.609f, 5.69f, 13.899f, 5.4f)
                curveTo(14.189f, 5.11f, 14.669f, 5.11f, 14.959f, 5.4f)
                lineTo(21.029f, 11.47f)
                curveTo(21.319f, 11.76f, 21.319f, 12.24f, 21.029f, 12.53f)
                lineTo(14.959f, 18.6f)
                curveTo(14.809f, 18.75f, 14.619f, 18.82f, 14.429f, 18.82f)
                close()
            }
            path(fill = SolidColor(Color(0xFFAAAAAA))) {
                moveTo(20.33f, 12.75f)
                horizontalLineTo(3.5f)
                curveTo(3.09f, 12.75f, 2.75f, 12.41f, 2.75f, 12f)
                curveTo(2.75f, 11.59f, 3.09f, 11.25f, 3.5f, 11.25f)
                horizontalLineTo(20.33f)
                curveTo(20.74f, 11.25f, 21.08f, 11.59f, 21.08f, 12f)
                curveTo(21.08f, 12.41f, 20.74f, 12.75f, 20.33f, 12.75f)
                close()
            }
        }.build()

        return _ArrowRight!!
    }

@Suppress("ObjectPropertyName")
private var _ArrowRight: ImageVector? = null
