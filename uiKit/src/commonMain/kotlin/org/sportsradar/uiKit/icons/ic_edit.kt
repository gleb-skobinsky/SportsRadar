package org.sportsradar.uiKit.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val IcEdit: ImageVector
    get() {
        if (Edit != null) {
            return Edit!!
        }
        Edit = ImageVector.Builder(
            name = "Edit",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            group(
                clipPathData = PathData {
                    moveTo(0f, 0f)
                    horizontalLineToRelative(24f)
                    verticalLineToRelative(24f)
                    horizontalLineToRelative(-24f)
                    close()
                }
            ) {
                path(
                    stroke = SolidColor(Color.White),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round
                ) {
                    moveTo(11f, 2f)
                    horizontalLineTo(9f)
                    curveTo(4f, 2f, 2f, 4f, 2f, 9f)
                    verticalLineTo(15f)
                    curveTo(2f, 20f, 4f, 22f, 9f, 22f)
                    horizontalLineTo(15f)
                    curveTo(20f, 22f, 22f, 20f, 22f, 15f)
                    verticalLineTo(13f)
                }
                path(
                    stroke = SolidColor(Color.White),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round
                ) {
                    moveTo(16.04f, 3.019f)
                    lineTo(8.16f, 10.899f)
                    curveTo(7.86f, 11.199f, 7.56f, 11.789f, 7.5f, 12.219f)
                    lineTo(7.07f, 15.229f)
                    curveTo(6.91f, 16.319f, 7.68f, 17.079f, 8.77f, 16.929f)
                    lineTo(11.78f, 16.499f)
                    curveTo(12.2f, 16.439f, 12.79f, 16.139f, 13.1f, 15.839f)
                    lineTo(20.98f, 7.959f)
                    curveTo(22.34f, 6.599f, 22.98f, 5.019f, 20.98f, 3.019f)
                    curveTo(18.98f, 1.019f, 17.4f, 1.659f, 16.04f, 3.019f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.White),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round
                ) {
                    moveTo(14.91f, 4.15f)
                    curveTo(15.58f, 6.54f, 17.45f, 8.41f, 19.85f, 9.09f)
                }
            }
        }.build()

        return Edit!!
    }

private var Edit: ImageVector? = null
