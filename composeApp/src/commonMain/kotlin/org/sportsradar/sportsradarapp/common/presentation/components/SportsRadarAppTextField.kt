package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

val DefaultTextFieldShape = RoundedCornerShape(4.dp)

@Composable
fun SportsRadarAppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    backgroundColor: Color = LocalSportsRadarTheme.colors.tertiary,
    textColor: Color = LocalSportsRadarTheme.colors.primaryContainer,
    placeholderColor: Color = LocalSportsRadarTheme.colors.onTertiary,
    shape: Shape = DefaultTextFieldShape,
    textStyle: TextStyle = LocalSportsRadarTheme.typography.bodyMedium,
    leftIcon: ImageVector? = null,
    rightIcon: @Composable (() -> Unit)? = null,
    maxLines: Int = 1,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.clip(shape).height(48.dp).background(backgroundColor),
        enabled = enabled,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        textStyle = textStyle.copy(color = textColor),
        keyboardOptions = keyboardOptions,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                leftIcon?.let {
                    SportsRadarAppLeadingIcon(it)
                }
                Box(Modifier.weight(1f)) {
                    if (value.isEmpty()) Text(
                        text = placeholder,
                        color = placeholderColor,
                        style = textStyle.copy(color = placeholderColor)
                    )
                    innerTextField()
                }
                rightIcon?.invoke()
            }
        },
        readOnly = readOnly,
        cursorBrush = SolidColor(LocalSportsRadarTheme.colors.primaryContainer)
    )
}

@Composable
private fun SportsRadarAppLeadingIcon(
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
) {
    ClickableIcon(
        iconVector = imageVector,
        modifier = modifier
            .drawIconGlow(LocalSportsRadarTheme.colors.primary)
    )
}

private fun Modifier.drawIconGlow(color: Color) = this
    .drawWithCache {
        val scrim = color.copy(alpha = 0.1f)
        val radius = 20.dp.toPx()
        onDrawBehind {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        scrim,
                        scrim,
                        Color.Transparent
                    ),
                    center = center,
                    radius = radius
                ),
                radius = radius
            )
        }
    }