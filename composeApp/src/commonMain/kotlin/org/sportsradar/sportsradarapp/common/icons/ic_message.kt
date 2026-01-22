package org.sportsradar.sportsradarapp.common.icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.sportsradar.uiKit.theme.PrimaryGradient

val Message: ImageVector
    get() {
        if (_Message != null) {
            return _Message!!
        }
        _Message = ImageVector.Builder(
            name = "Message",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            val brush = PrimaryGradient
            path(
                fill = brush
            ) {
                moveTo(12f, 22.81f)
                curveTo(11.31f, 22.81f, 10.66f, 22.46f, 10.2f, 21.85f)
                lineTo(8.7f, 19.85f)
                curveTo(8.67f, 19.81f, 8.55f, 19.76f, 8.5f, 19.75f)
                horizontalLineTo(8f)
                curveTo(3.83f, 19.75f, 1.25f, 18.62f, 1.25f, 13f)
                verticalLineTo(8f)
                curveTo(1.25f, 3.58f, 3.58f, 1.25f, 8f, 1.25f)
                horizontalLineTo(16f)
                curveTo(20.42f, 1.25f, 22.75f, 3.58f, 22.75f, 8f)
                verticalLineTo(13f)
                curveTo(22.75f, 17.42f, 20.42f, 19.75f, 16f, 19.75f)
                horizontalLineTo(15.5f)
                curveTo(15.42f, 19.75f, 15.35f, 19.79f, 15.3f, 19.85f)
                lineTo(13.8f, 21.85f)
                curveTo(13.34f, 22.46f, 12.69f, 22.81f, 12f, 22.81f)
                close()
                moveTo(8f, 2.75f)
                curveTo(4.42f, 2.75f, 2.75f, 4.42f, 2.75f, 8f)
                verticalLineTo(13f)
                curveTo(2.75f, 17.52f, 4.3f, 18.25f, 8f, 18.25f)
                horizontalLineTo(8.5f)
                curveTo(9.01f, 18.25f, 9.59f, 18.54f, 9.9f, 18.95f)
                lineTo(11.4f, 20.95f)
                curveTo(11.75f, 21.41f, 12.25f, 21.41f, 12.6f, 20.95f)
                lineTo(14.1f, 18.95f)
                curveTo(14.43f, 18.51f, 14.95f, 18.25f, 15.5f, 18.25f)
                horizontalLineTo(16f)
                curveTo(19.58f, 18.25f, 21.25f, 16.58f, 21.25f, 13f)
                verticalLineTo(8f)
                curveTo(21.25f, 4.42f, 19.58f, 2.75f, 16f, 2.75f)
                horizontalLineTo(8f)
                close()
            }
            path(fill = brush) {
                moveTo(12f, 12f)
                curveTo(11.44f, 12f, 11f, 11.55f, 11f, 11f)
                curveTo(11f, 10.45f, 11.45f, 10f, 12f, 10f)
                curveTo(12.55f, 10f, 13f, 10.45f, 13f, 11f)
                curveTo(13f, 11.55f, 12.56f, 12f, 12f, 12f)
                close()
            }
            path(fill = brush) {
                moveTo(16f, 12f)
                curveTo(15.44f, 12f, 15f, 11.55f, 15f, 11f)
                curveTo(15f, 10.45f, 15.45f, 10f, 16f, 10f)
                curveTo(16.55f, 10f, 17f, 10.45f, 17f, 11f)
                curveTo(17f, 11.55f, 16.56f, 12f, 16f, 12f)
                close()
            }
            path(fill = brush) {
                moveTo(8f, 12f)
                curveTo(7.44f, 12f, 7f, 11.55f, 7f, 11f)
                curveTo(7f, 10.45f, 7.45f, 10f, 8f, 10f)
                curveTo(8.55f, 10f, 9f, 10.45f, 9f, 11f)
                curveTo(9f, 11.55f, 8.56f, 12f, 8f, 12f)
                close()
            }
        }.build()

        return _Message!!
    }

@Suppress("ObjectPropertyName")
private var _Message: ImageVector? = null
