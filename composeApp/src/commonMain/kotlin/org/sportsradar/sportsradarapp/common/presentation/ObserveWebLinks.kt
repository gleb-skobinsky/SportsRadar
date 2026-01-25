package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
expect fun NavHostController.handleWebDeepLinkOnStart()