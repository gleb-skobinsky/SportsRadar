package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator

@Composable
actual fun KMPNavigator.handleWebDeepLinkOnStart() = Unit

@Composable
actual fun rememberActivityFinisher() = ActivityFinisher.NoOp