package com.sportsradar.util

import io.ktor.server.application.Application
import io.ktor.server.config.ApplicationConfig
import io.ktor.server.plugins.cors.CORSConfig

internal fun ApplicationConfig.configListOrNull(
    path: String,
): List<ApplicationConfig>? {
    return runCatching { configList(path) }.getOrNull()
}

internal fun CORSConfig.configureDevClients(
    application: Application,
) {
    application.environment.log.info("DEV CLIENTS")
    application.environment.config
        .configListOrNull("devClients")
        ?.forEach { cfg ->
            val host = cfg.propertyOrNull("host")?.getString()
            val scheme = cfg.propertyOrNull("scheme")?.getString()
            application.environment.log.info("Allowed dev client: host $host scheme $scheme")
            if (host != null && scheme != null) {
                allowHost(
                    host = host,
                    schemes = listOf(scheme)
                )
            }
        }
}