package org.sportsradar.uiKit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import org.sportsradar.uiKit.utils.diagonalGradient

@Immutable
data class SportsRadarColors(
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val onPrimaryContainer: Color,
    val secondary: Color,
    val onSecondary: Color,
    val secondaryContainer: Color,
    val onSecondaryContainer: Color,
    val tertiary: Color,
    val onTertiary: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val onSurfaceVariant: Color,
    val surfaceTint: Color,
    val error: Color,
    val onError: Color,
    val surfaceContainerHigh: Color,
    val secondaryButton: Color,
    val secondaryButtonBorder: Color,
)

val SportsRadarDarkColorScheme = SportsRadarColors(
    primary = Color(244, 128, 102),
    onPrimary = Color(48, 48, 48),
    primaryContainer = Color(255, 255, 255),
    onPrimaryContainer = Color(20, 14, 23),
    secondary = Color(231, 231, 231),
    onSecondary = Color(170, 170, 170),
    secondaryContainer = Color(170, 170, 170),
    onSecondaryContainer = Color.Unspecified,
    tertiary = Color(64, 64, 64),
    onTertiary = Color(119, 117, 127),
    surface = Color(48, 48, 48),
    surfaceVariant = Color(64, 64, 64),
    onSurfaceVariant = Color(244, 128, 102),
    surfaceTint = Color(0x80000000),
    error = Color(255, 60, 99),
    onError = Color.White,
    surfaceContainerHigh = Color(64f, 64f, 64f, 0.8f),
    secondaryButton = Color(82, 82, 82, 255),
    secondaryButtonBorder = Color(102, 102, 102, 255)
)

val SportsRadarLightColorScheme = SportsRadarColors(
    primary = Color(244, 128, 102),
    onPrimary = Color.White,
    primaryContainer = Color(255, 230, 224),
    onPrimaryContainer = Color(72, 32, 24),
    secondary = Color(64, 64, 64),
    onSecondary = Color(120, 120, 120),
    secondaryContainer = Color(230, 230, 230),
    onSecondaryContainer = Color(64, 64, 64),
    tertiary = Color(210, 210, 210),
    onTertiary = Color(120, 120, 120),
    surface = Color(248, 248, 248),
    surfaceVariant = Color(235, 235, 235),
    onSurfaceVariant = Color(244, 128, 102),
    surfaceTint = Color(0x14000000),
    error = Color(220, 50, 90),
    onError = Color.White,
    surfaceContainerHigh = Color(255, 255, 255),
    secondaryButton = Color(230, 230, 230),
    secondaryButtonBorder = Color(200, 200, 200)
)


private val primaryGradientColors = listOf(
    Color(244, 128, 102),
    Color(245, 149, 129),
    Color(245, 177, 164)
)

private val dangerColors = listOf(
    Color(255, 162, 110),
    Color(255, 60, 99)
)

val PrimaryGradient: Brush = Brush.linearGradient(
    colors = primaryGradientColors,
    start = Offset.Zero,
    end = Offset.Infinite
)

fun Modifier.primaryGradient() = this.diagonalGradient(primaryGradientColors)

fun Modifier.dangerGradient() = this.diagonalGradient(dangerColors)