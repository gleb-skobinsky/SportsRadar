package org.sportsradar.sportsradarapp.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
actual fun rememberTabHistory(): TabHistory = rememberTabHistoryCommon()

@Composable
actual fun rememberController(): NavHostController = rememberNavController()