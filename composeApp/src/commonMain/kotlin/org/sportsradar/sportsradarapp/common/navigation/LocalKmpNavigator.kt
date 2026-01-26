package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.staticCompositionLocalOf

val LocalKmpNavigator = staticCompositionLocalOf<KMPNavigator> {
    error("No KMPNavigator provided")
}