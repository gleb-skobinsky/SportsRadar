package org.sportsradar.sportsradarapp.common.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.sportsradar.uiKit.icons.Eye
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
fun SportsRadarAppPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    backgroundColor: Color = LocalSportsRadarTheme.colors.tertiary,
    textColor: Color = LocalSportsRadarTheme.colors.primaryContainer,
    placeholderColor: Color = LocalSportsRadarTheme.colors.onTertiary,
    shape: Shape = RoundedCornerShape(4.dp),
    leftIcon: ImageVector? = null,
    enabled: Boolean = true,
) {
    var passwordShown by rememberSaveable { mutableStateOf(false) }
    SportsRadarAppTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        placeholder = placeholder,
        backgroundColor = backgroundColor,
        textColor = textColor,
        placeholderColor = placeholderColor,
        shape = shape,
        leftIcon = leftIcon,
        enabled = enabled,
        maxLines = 1,
        visualTransformation = if (passwordShown)
            VisualTransformation.None
        else
            PasswordVisualTransformation('•'),
        rightIcon = {
            ClickableIcon(
                iconVector = Eye,
                modifier = Modifier.strikeThrough(passwordShown, textColor),
                tintColor = LocalSportsRadarTheme.colors.primaryContainer
            ) {
                passwordShown = !passwordShown
            }
        }
    )
}

fun Modifier.strikeThrough(
    enabled: Boolean,
    color: Color,
    heightRatio: Float = 0.2f
): Modifier = this
    .drawWithContent {
        drawContent()
        if (enabled) {
            val yCorrection = size.height * heightRatio
            drawLine(
                color = color,
                start = Offset(0f, yCorrection),
                end = Offset(size.width, size.height - yCorrection),
                strokeWidth = 2.dp.toPx()
            )
        }
    }
