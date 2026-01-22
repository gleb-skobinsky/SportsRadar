package com.sportsradar.plugins

import com.sportsradar.email.data.AppSecrets
import com.sportsradar.jwt.JWTConfig.Companion.JWT_AUTH_ID
import io.bkbn.kompendium.core.plugin.NotarizedApplication
import io.bkbn.kompendium.oas.OpenApiSpec
import io.bkbn.kompendium.oas.component.Components
import io.bkbn.kompendium.oas.info.Contact
import io.bkbn.kompendium.oas.info.Info
import io.bkbn.kompendium.oas.info.License
import io.bkbn.kompendium.oas.security.BearerAuth
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.swagger.swaggerUI
import io.ktor.server.routing.routing
import java.net.URI

fun Application.configureSwagger(secrets: AppSecrets) {
    if (secrets.isDebug) {
        install(NotarizedApplication()) {
            spec = {
                OpenApiSpec(
                    openapi = "3.0.3",
                    info = Info(
                        title = "SportsRadar API",
                        version = "1.0.0",
                        summary = "Swagger documentation for the SportsRadar backend API",
                        description = "Here you can find all the necessary endpoints to consume the SportsRadar API",
                        termsOfService = URI.create("https://example.com"),
                        contact = Contact("Gleb Gutnik"),
                        license = License("Open license")
                    ),
                    components = Components(
                        securitySchemes = mutableMapOf(
                            JWT_AUTH_ID to BearerAuth(bearerFormat = "JWT")
                        )
                    )
                )
            }
        }
        routing {
            swaggerUI(path = "openapi", swaggerFile = "openapi/openapi.yaml")
        }
    }
}
