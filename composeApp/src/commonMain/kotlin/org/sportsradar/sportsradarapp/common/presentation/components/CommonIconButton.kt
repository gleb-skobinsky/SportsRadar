package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CommonIconButton(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    color: Color = LocalSportsRadarTheme.colors.primaryContainer,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.requiredSize(48.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = color
        )
    }
}