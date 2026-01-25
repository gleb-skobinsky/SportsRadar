package org.sportsradar.sportsradarapp.common.presentation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavUri
import kotlinx.browser.window

actual fun NavHostController.handleWebDeepLinkOnStart() {
    val deeplink = window.location.href
    val path = window.location.pathname

    println("WEB LINK: $deeplink PATH: $path")

    if (path.isNotBlank() && path != "/") {
        navigate(
            deepLink = NavUri(deeplink),
            navOptions = NavOptions.Builder().setLaunchSingleTop(true).build()
        )
    }
}