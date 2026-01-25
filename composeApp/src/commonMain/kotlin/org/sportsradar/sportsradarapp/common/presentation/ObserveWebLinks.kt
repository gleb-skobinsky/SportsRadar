package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator

@Composable
expect fun KMPNavigator.handleWebDeepLinkOnStart()

fun interface ActivityFinisher {
    fun finish()

    companion object {
        val NoOp = ActivityFinisher {}
    }
}

@Composable
expect fun rememberActivityFinisher(): ActivityFinisher