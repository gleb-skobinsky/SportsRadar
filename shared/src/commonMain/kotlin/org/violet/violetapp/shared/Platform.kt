package org.violet.violetapp.shared

enum class Platform {
    Jvm,
    Android,
    IOS,
    Web
}

expect fun getPlatform(): Platform