package org.sportsradar.uiKit.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import org.sportsradar.uiKit.ripple.node.opacityRipple.opacityRipple

@Stable
object LocalSportsRadarTheme {
    val colors: SportsRadarColors
        @Composable @ReadOnlyComposable get() = LocalSportsRadarColors.current

    val typography: Typography
        @Composable @ReadOnlyComposable get() = LocalSportsRadarTypography.current
}

val LocalSportsRadarColors = staticCompositionLocalOf { SportsRadarAppColorScheme }
val LocalSportsRadarTypography = staticCompositionLocalOf<Typography> {
    error("No typography provided")
}


private const val TextSelectionBackgroundOpacity = 0.4f

@Composable
fun rememberTextSelectionColors(colorScheme: SportsRadarColors): TextSelectionColors {
    val primaryColor = colorScheme.primary
    return remember(primaryColor) {
        TextSelectionColors(
            handleColor = primaryColor,
            backgroundColor = primaryColor.copy(alpha = TextSelectionBackgroundOpacity),
        )
    }
}


@Composable
fun SportsRadarTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = SportsRadarAppColorScheme
    CompositionLocalProvider(
        LocalSportsRadarTypography provides sportsradarAppTypography,
        LocalSportsRadarColors provides SportsRadarAppColorScheme,
        LocalIndication provides opacityRipple(300, 300),
        LocalTextSelectionColors provides rememberTextSelectionColors(
            colorScheme
        ),
        content = content
    )
}