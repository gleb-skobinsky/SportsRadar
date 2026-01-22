package com.violet.features.auth.routes

import org.violet.violetapp.shared.auth.data.TokenData
import com.violet.features.auth.models.RefreshToken
import com.violet.jwt.JWTConfig
import com.violet.jwt.JWTConfig.Companion.ACCESS_EXPIRATION_TIMEOUT
import com.violet.jwt.JWTConfig.Companion.REFRESH_EXPIRATION_TIMEOUT
import com.violet.jwt.TokenType
import com.violet.jwt.createToken
import com.violet.jwt.verifyToken
import com.violet.shared.RepositoriesTags
import org.violet.violetapp.shared.common.data.Endpoints
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route

internal fun Routing.refreshTokenRoute(jwtConfig: JWTConfig) {
    route(Endpoints.Auth.RefreshToken) {
        install(NotarizedRoute()) {
            tags = setOf(RepositoriesTags.AUTH)
            post = PostInfo.builder {
                summary("Refresh")
                description("Refresh JWT token")
                request {
                    description("Refresh a token")
                    requestType<RefreshToken>()
                }
                response {
                    description("Token successfully refreshed")
                    responseCode(HttpStatusCode.OK)
                    responseType<TokenData>()
                }
            }
        }
        post {
            // Extract the refresh token from the request
            val refreshToken = call.receive<RefreshToken>()

            // Verify the refresh token and obtain the user
            val email = jwtConfig.verifyToken(
                token = refreshToken.token,
                type = TokenType.RefreshToken
            ) ?: run {
                call.respond(HttpStatusCode.Forbidden, "Invalid refresh token")
                return@post
            }

            // Create new access and refresh tokens for the user
            val newAccessToken = jwtConfig.createToken(
                email = email,
                type = TokenType.AccessToken,
                expiration = ACCESS_EXPIRATION_TIMEOUT
            )
            val newRefreshToken = jwtConfig.createToken(
                email = email,
                type = TokenType.RefreshToken,
                expiration = REFRESH_EXPIRATION_TIMEOUT
            )

            // Respond with the new tokens
            call.respond(TokenData(newAccessToken, newRefreshToken))
        }
    }
}
