package com.sportsradar.features.auth.routes

import org.sportsradar.sportsradarapp.shared.auth.data.TokenData
import org.sportsradar.sportsradarapp.shared.auth.data.RefreshTokenRequest
import com.sportsradar.jwt.JWTConfig
import com.sportsradar.jwt.JWTConfig.Companion.ACCESS_EXPIRATION_TIMEOUT
import com.sportsradar.jwt.JWTConfig.Companion.REFRESH_EXPIRATION_TIMEOUT
import com.sportsradar.jwt.TokenType
import com.sportsradar.jwt.createToken
import com.sportsradar.jwt.verifyToken
import com.sportsradar.shared.RepositoriesTags
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints
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
                    requestType<RefreshTokenRequest>()
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
            val refreshToken = call.receive<RefreshTokenRequest>()

            // Verify the refresh token and obtain the user
            val email = jwtConfig.verifyToken(
                token = refreshToken.refreshToken,
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
