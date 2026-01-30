package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sportsradar.uiKit.icons.ArrowRight
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import org.sportsradar.uiKit.theme.dangerGradient
import org.sportsradar.uiKit.theme.primaryGradient

enum class SportsRadarAppButtonType {
    Primary,
    Secondary,
    Danger
}

@Immutable
data class SportsRadarButtonColors(
    val containerColor: Color,
    val contentColor: Color,
    val disabledContainerColor: Color,
    val disabledContentColor: Color,
    val borderColor: Color = Color.Unspecified,
)

@Composable
private fun SportsRadarAppButtonType.toColors(): SportsRadarButtonColors = when (this) {
    SportsRadarAppButtonType.Primary -> SportsRadarButtonColors(
        containerColor = LocalSportsRadarTheme.colors.primary,
        contentColor = LocalSportsRadarTheme.colors.onPrimary,
        disabledContainerColor = LocalSportsRadarTheme.colors.tertiary,
        disabledContentColor = LocalSportsRadarTheme.colors.onTertiary,
    )

    SportsRadarAppButtonType.Danger -> SportsRadarButtonColors(
        containerColor = LocalSportsRadarTheme.colors.error,
        contentColor = LocalSportsRadarTheme.colors.onError,
        disabledContainerColor = LocalSportsRadarTheme.colors.tertiary,
        disabledContentColor = LocalSportsRadarTheme.colors.onTertiary,
    )

    SportsRadarAppButtonType.Secondary -> SportsRadarButtonColors(
        containerColor = LocalSportsRadarTheme.colors.secondaryButton,
        contentColor = LocalSportsRadarTheme.colors.secondary,
        disabledContainerColor = LocalSportsRadarTheme.colors.tertiary,
        disabledContentColor = LocalSportsRadarTheme.colors.onTertiary,
        borderColor = LocalSportsRadarTheme.colors.secondaryButtonBorder
    )
}

@Composable
fun SportsRadarAppButton(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leftIcon: ImageVector? = null,
    rightIcon: ImageVector? = ArrowRight,
    type: SportsRadarAppButtonType = SportsRadarAppButtonType.Primary,
    onClick: () -> Unit
) {
    val enabledInternal = enabled && !isLoading
    val colors = type.toColors()
    val textColor = if (enabled) colors.contentColor else colors.disabledContentColor
    Box(
        modifier = modifier
            .height(48.dp)
            .clip(CircleShape)
            .colorOrGradient(type, enabled, colors)
            .clickable(onClick = onClick, enabled = enabledInternal)
            .padding(ButtonDefaults.ContentPadding),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            SportsRadarAppTwinklingProgress(textColor)
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                leftIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Button left icon",
                        modifier = Modifier.size(24.dp),
                        tint = textColor
                    )
                    8.dp.HorizontalSpacer()
                }
                Text(
                    text = label.uppercase(),
                    color = textColor,
                    fontSize = 16.sp
                )
                rightIcon?.let {
                    8.dp.HorizontalSpacer()
                    Icon(
                        imageVector = it,
                        contentDescription = "Button right icon",
                        modifier = Modifier.size(24.dp),
                        tint = textColor
                    )
                }
            }
        }
    }
}

private fun Modifier.colorOrGradient(
    type: SportsRadarAppButtonType,
    enabled: Boolean,
    colors: SportsRadarButtonColors
) = when {
    type == SportsRadarAppButtonType.Primary && enabled -> this.primaryGradient()
    type == SportsRadarAppButtonType.Danger && enabled -> this.dangerGradient()
    enabled -> this.background(colors.containerColor).maybeApplyBorder(colors.borderColor)
    else -> this.background(colors.disabledContainerColor).maybeApplyBorder(colors.borderColor)
}

private fun Modifier.maybeApplyBorder(
    borderColor: Color,
) = this.then(
    if (borderColor.isSpecified) {
        Modifier.border(1.dp, borderColor, CircleShape)
    } else {
        Modifier
    }
)