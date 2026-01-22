package org.sportsradar.sportsradarapp.shared

enum class Platform {
    Jvm,
    Android,
    IOS,
    Web
}

expect fun getPlatform(): Platform