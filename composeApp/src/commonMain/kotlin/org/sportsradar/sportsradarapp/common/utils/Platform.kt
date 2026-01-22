package org.sportsradar.sportsradarapp.common.utils

enum class Platform {
    Android,
    IOS,
    Web,
    Desktop;

    val isAndroid get() = this == Android
    val isIOS get() = this == IOS
    val isWeb get() = this == Web
    val isDesktop get() = this == Desktop
}

expect val platform: Platform