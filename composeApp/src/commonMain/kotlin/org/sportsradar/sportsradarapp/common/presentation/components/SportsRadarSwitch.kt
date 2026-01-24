package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.layout.Column
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SportsRadarAppSwitch(
    value: Boolean,
    onValueChange: (Boolean) -> Unit,
    label: String = "",
    enabled: Boolean = true
) {
    Column {
        if (label.isNotEmpty()) {
            Text(
                text = label,
                style = LocalSportsRadarTheme.typography.bodyMedium,
                color = LocalSportsRadarTheme.colors.secondaryContainer
            )
        }
        Switch(
            checked = value,
            onCheckedChange = onValueChange,
            enabled = enabled,
            colors = SwitchDefaults.colors(
                checkedThumbColor = LocalSportsRadarTheme.colors.primaryContainer,
                checkedIconColor = LocalSportsRadarTheme.colors.primary,
                checkedTrackColor = LocalSportsRadarTheme.colors.primary,

                uncheckedThumbColor = LocalSportsRadarTheme.colors.onTertiary,
                uncheckedIconColor = LocalSportsRadarTheme.colors.tertiary,
                uncheckedTrackColor = LocalSportsRadarTheme.colors.tertiary
            )
        )
    }
}