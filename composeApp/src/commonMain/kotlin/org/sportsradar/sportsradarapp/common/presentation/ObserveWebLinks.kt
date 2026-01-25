package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
expect fun NavHostController.handleWebDeepLinkOnStart()

fun interface ActivityFinisher {
    fun finish()

    companion object {
        val NoOp = ActivityFinisher {}
    }
}

@Composable
expect fun rememberActivityFinisher(): ActivityFinisher