package org.sportsradar.sportsradarapp.common.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.AuthConfig
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.sportsradar.sportsradarapp.auth.data.UserSecureStorage
import org.sportsradar.sportsradarapp.auth.domain.AuthRepository
import org.sportsradar.sportsradarapp.common.utils.localhost

private const val NETWORK_TIMEOUT = 30_000L

fun configureKtorClient(
    json: Json,
    userSecureStorage: UserSecureStorage,
    authRepository: AuthRepository,
) = HttpClient {
    expectSuccess = false
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println("Ktor Client: $message")
            }
        }
    }

    defaultRequest {
        url {
            protocol = localhost.protocol
            host = localhost.host
            port = localhost.port
        }
        contentType(ContentType.Application.Json)
    }

    install(HttpTimeout) {
        requestTimeoutMillis = NETWORK_TIMEOUT
        connectTimeoutMillis = NETWORK_TIMEOUT
        socketTimeoutMillis = NETWORK_TIMEOUT
    }

    install(ContentNegotiation) {
        json(json)
    }

    install(Auth) {
        configureBearerAuth(userSecureStorage, authRepository)
    }
}

internal fun AuthConfig.configureBearerAuth(
    userSecureStorage: UserSecureStorage,
    authRepository: AuthRepository,
) {
    bearer {
        loadTokens {
            userSecureStorage.getTokens()?.let { tokens ->
                BearerTokens(
                    accessToken = tokens.accessToken,
                    refreshToken = tokens.refreshToken
                )
            }
        }

        refreshTokens {
            authRepository.refresh().data?.let { tokens ->
                BearerTokens(
                    accessToken = tokens.accessToken,
                    refreshToken = tokens.refreshToken
                )
            }
        }
    }
}