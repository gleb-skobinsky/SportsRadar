package org.sportsradar.sportsradarapp.common.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathData
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Profile: ImageVector
    get() {
        if (IcProfile != null) {
            return IcProfile!!
        }
        IcProfile = ImageVector.Builder(
            name = "Profile",
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
                    moveTo(12.16f, 10.87f)
                    curveTo(12.06f, 10.86f, 11.94f, 10.86f, 11.83f, 10.87f)
                    curveTo(9.45f, 10.79f, 7.56f, 8.84f, 7.56f, 6.44f)
                    curveTo(7.56f, 3.99f, 9.54f, 2f, 12f, 2f)
                    curveTo(14.45f, 2f, 16.44f, 3.99f, 16.44f, 6.44f)
                    curveTo(16.43f, 8.84f, 14.54f, 10.79f, 12.16f, 10.87f)
                    close()
                }
                path(
                    stroke = SolidColor(Color.White),
                    strokeLineWidth = 1.5f,
                    strokeLineCap = StrokeCap.Round,
                    strokeLineJoin = StrokeJoin.Round
                ) {
                    moveTo(7.16f, 14.56f)
                    curveTo(4.74f, 16.18f, 4.74f, 18.82f, 7.16f, 20.43f)
                    curveTo(9.91f, 22.27f, 14.42f, 22.27f, 17.17f, 20.43f)
                    curveTo(19.59f, 18.81f, 19.59f, 16.17f, 17.17f, 14.56f)
                    curveTo(14.43f, 12.73f, 9.92f, 12.73f, 7.16f, 14.56f)
                    close()
                }
            }
        }.build()

        return IcProfile!!
    }

private var IcProfile: ImageVector? = null
