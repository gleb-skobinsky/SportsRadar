package org.sportsradar.uiKit.icons

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import org.sportsradar.uiKit.theme.PrimaryGradient

val LockHidden: ImageVector
    get() {
        if (_LockHidden != null) {
            return _LockHidden!!
        }
        _LockHidden = ImageVector.Builder(
            name = "LockHidden",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = PrimaryGradient
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
                fill = PrimaryGradient
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
            path(
                fill = PrimaryGradient
            ) {
                moveTo(8f, 17f)
                curveTo(7.87f, 17f, 7.74f, 16.97f, 7.62f, 16.92f)
                curveTo(7.49f, 16.87f, 7.39f, 16.8f, 7.29f, 16.71f)
                curveTo(7.11f, 16.52f, 7f, 16.27f, 7f, 16f)
                curveTo(7f, 15.87f, 7.03f, 15.74f, 7.08f, 15.62f)
                curveTo(7.13f, 15.49f, 7.2f, 15.39f, 7.29f, 15.29f)
                curveTo(7.39f, 15.2f, 7.49f, 15.13f, 7.62f, 15.08f)
                curveTo(7.98f, 14.92f, 8.43f, 15.01f, 8.71f, 15.29f)
                curveTo(8.8f, 15.39f, 8.87f, 15.5f, 8.92f, 15.62f)
                curveTo(8.97f, 15.74f, 9f, 15.87f, 9f, 16f)
                curveTo(9f, 16.26f, 8.89f, 16.52f, 8.71f, 16.71f)
                curveTo(8.52f, 16.89f, 8.26f, 17f, 8f, 17f)
                close()
            }
            path(
                fill = PrimaryGradient
            ) {
                moveTo(12f, 17f)
                curveTo(11.74f, 17f, 11.48f, 16.89f, 11.29f, 16.71f)
                curveTo(11.11f, 16.52f, 11f, 16.27f, 11f, 16f)
                curveTo(11f, 15.87f, 11.02f, 15.74f, 11.08f, 15.62f)
                curveTo(11.13f, 15.5f, 11.2f, 15.39f, 11.29f, 15.29f)
                curveTo(11.52f, 15.06f, 11.87f, 14.95f, 12.19f, 15.02f)
                curveTo(12.26f, 15.03f, 12.32f, 15.05f, 12.38f, 15.08f)
                curveTo(12.44f, 15.1f, 12.5f, 15.13f, 12.56f, 15.17f)
                curveTo(12.61f, 15.2f, 12.66f, 15.25f, 12.71f, 15.29f)
                curveTo(12.8f, 15.39f, 12.87f, 15.5f, 12.92f, 15.62f)
                curveTo(12.97f, 15.74f, 13f, 15.87f, 13f, 16f)
                curveTo(13f, 16.27f, 12.89f, 16.52f, 12.71f, 16.71f)
                curveTo(12.66f, 16.75f, 12.61f, 16.79f, 12.56f, 16.83f)
                curveTo(12.5f, 16.87f, 12.44f, 16.9f, 12.38f, 16.92f)
                curveTo(12.32f, 16.95f, 12.26f, 16.97f, 12.19f, 16.98f)
                curveTo(12.13f, 16.99f, 12.06f, 17f, 12f, 17f)
                close()
            }
            path(
                fill = PrimaryGradient
            ) {
                moveTo(16f, 17f)
                curveTo(15.73f, 17f, 15.48f, 16.89f, 15.29f, 16.71f)
                curveTo(15.2f, 16.61f, 15.13f, 16.5f, 15.08f, 16.38f)
                curveTo(15.03f, 16.26f, 15f, 16.13f, 15f, 16f)
                curveTo(15f, 15.74f, 15.11f, 15.48f, 15.29f, 15.29f)
                curveTo(15.34f, 15.25f, 15.39f, 15.21f, 15.44f, 15.17f)
                curveTo(15.5f, 15.13f, 15.56f, 15.1f, 15.62f, 15.08f)
                curveTo(15.68f, 15.05f, 15.74f, 15.03f, 15.8f, 15.02f)
                curveTo(16.13f, 14.95f, 16.47f, 15.06f, 16.71f, 15.29f)
                curveTo(16.89f, 15.48f, 17f, 15.73f, 17f, 16f)
                curveTo(17f, 16.13f, 16.97f, 16.26f, 16.92f, 16.38f)
                curveTo(16.87f, 16.51f, 16.8f, 16.61f, 16.71f, 16.71f)
                curveTo(16.52f, 16.89f, 16.26f, 17f, 16f, 17f)
                close()
            }
        }.build()

        return _LockHidden!!
    }

@Suppress("ObjectPropertyName")
private var _LockHidden: ImageVector? = null
