package org.sportsradar.sportsradarapp.common.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val History: ImageVector
    get() {
        if (Document != null) {
            return Document!!
        }
        Document = ImageVector.Builder(
            name = "Document",
            defaultWidth = 32.dp,
            defaultHeight = 32.dp,
            viewportWidth = 32f,
            viewportHeight = 32f
        ).apply {
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(19.999f, 30.333f)
                horizontalLineTo(11.999f)
                curveTo(4.759f, 30.333f, 1.666f, 27.24f, 1.666f, 20f)
                verticalLineTo(12f)
                curveTo(1.666f, 4.76f, 4.759f, 1.666f, 11.999f, 1.666f)
                horizontalLineTo(19.999f)
                curveTo(27.239f, 1.666f, 30.333f, 4.76f, 30.333f, 12f)
                verticalLineTo(20f)
                curveTo(30.333f, 27.24f, 27.239f, 30.333f, 19.999f, 30.333f)
                close()
                moveTo(11.999f, 3.667f)
                curveTo(5.853f, 3.667f, 3.666f, 5.853f, 3.666f, 12f)
                verticalLineTo(20f)
                curveTo(3.666f, 26.146f, 5.853f, 28.333f, 11.999f, 28.333f)
                horizontalLineTo(19.999f)
                curveTo(26.146f, 28.333f, 28.333f, 26.146f, 28.333f, 20f)
                verticalLineTo(12f)
                curveTo(28.333f, 5.853f, 26.146f, 3.667f, 19.999f, 3.667f)
                horizontalLineTo(11.999f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(21f, 13f)
                horizontalLineTo(11f)
                curveTo(10.453f, 13f, 10f, 12.547f, 10f, 12f)
                curveTo(10f, 11.453f, 10.453f, 11f, 11f, 11f)
                horizontalLineTo(21f)
                curveTo(21.547f, 11f, 22f, 11.453f, 22f, 12f)
                curveTo(22f, 12.547f, 21.547f, 13f, 21f, 13f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(21f, 21f)
                horizontalLineTo(11f)
                curveTo(10.453f, 21f, 10f, 20.547f, 10f, 20f)
                curveTo(10f, 19.453f, 10.453f, 19f, 11f, 19f)
                horizontalLineTo(21f)
                curveTo(21.547f, 19f, 22f, 19.453f, 22f, 20f)
                curveTo(22f, 20.547f, 21.547f, 21f, 21f, 21f)
                close()
            }
        }.build()

        return Document!!
    }

private var Document: ImageVector? = null
