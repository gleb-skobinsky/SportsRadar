package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ClickableIcon(
    iconVector: ImageVector,
    modifier: Modifier = Modifier,
    tintColor: Color? = null,
    onClick: (() -> Unit)? = null
) {
    Box(
        modifier = Modifier.size(40.dp)
            .clip(CircleShape)
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            imageVector = iconVector,
            contentDescription = null,
            modifier = modifier.size(24.dp),
            colorFilter = tintColor?.let { ColorFilter.tint(it) }
        )
    }
}
