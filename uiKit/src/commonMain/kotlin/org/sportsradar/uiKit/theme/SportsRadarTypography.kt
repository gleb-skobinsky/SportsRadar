package org.sportsradar.uiKit.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import org.sportsradar.uiKit.resources.UiKitRes
import org.sportsradar.uiKit.resources.futura_bold
import org.sportsradar.uiKit.resources.futura_medium

internal inline val sportsradarAppTypography: Typography
    @Composable
    @NonRestartableComposable
    get() {
        val bold = FontFamily(Font(UiKitRes.font.futura_bold))
        val medium = FontFamily(Font(UiKitRes.font.futura_medium))
        return Typography(
            headlineLarge = TextStyle(
                fontFamily = bold,
                fontSize = 32.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Bold
            ),
            bodyLarge = TextStyle(
                fontFamily = bold,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            bodyMedium = TextStyle(
                fontFamily = medium,
                fontSize = 16.sp,
                fontWeight = FontWeight(450)
            ),
            bodySmall = TextStyle(
                fontFamily = medium,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 16.sp,
            ),
        )
    }