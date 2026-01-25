package com.sportsradar.util

import io.ktor.server.config.ApplicationConfig

internal fun ApplicationConfig.configListOrNull(
    path: String,
): List<ApplicationConfig>? {
    return runCatching { configList(path) }.getOrNull()
}