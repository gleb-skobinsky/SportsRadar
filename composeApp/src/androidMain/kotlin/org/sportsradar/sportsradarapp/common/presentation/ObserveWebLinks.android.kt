package org.sportsradar.sportsradarapp.common.presentation

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator

@Composable
actual fun KMPNavigator.handleWebDeepLinkOnStart() = Unit

@Composable
actual fun rememberActivityFinisher(): ActivityFinisher {
    val activity = LocalActivity.current
    return remember(activity) {
        ActivityFinisher {
            activity?.finish()
        }
    }
}