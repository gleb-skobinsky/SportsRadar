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

val Favorites: ImageVector
    get() {
        if (IcFavorites != null) {
            return IcFavorites!!
        }
        IcFavorites = ImageVector.Builder(
            name = "Favorites",
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
                    moveTo(12.62f, 20.81f)
                    curveTo(12.28f, 20.93f, 11.72f, 20.93f, 11.38f, 20.81f)
                    curveTo(8.48f, 19.82f, 2f, 15.69f, 2f, 8.69f)
                    curveTo(2f, 5.6f, 4.49f, 3.1f, 7.56f, 3.1f)
                    curveTo(9.38f, 3.1f, 10.99f, 3.98f, 12f, 5.34f)
                    curveTo(13.01f, 3.98f, 14.63f, 3.1f, 16.44f, 3.1f)
                    curveTo(19.51f, 3.1f, 22f, 5.6f, 22f, 8.69f)
                    curveTo(22f, 15.69f, 15.52f, 19.82f, 12.62f, 20.81f)
                    close()
                }
            }
        }.build()

        return IcFavorites!!
    }

private var IcFavorites: ImageVector? = null

