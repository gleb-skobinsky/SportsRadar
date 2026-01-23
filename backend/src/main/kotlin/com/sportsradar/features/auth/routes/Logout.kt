package com.sportsradar.features.auth.routes

import com.sportsradar.features.users.repository.TokenRepository
import com.sportsradar.shared.RepositoriesTags
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.sportsradar.sportsradarapp.shared.auth.data.UserLogoutRequest
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints

internal fun Routing.logoutRoute(
    tokenRepository: TokenRepository,
) {
    route(Endpoints.Auth.Logout) {
        install(NotarizedRoute()) {
            tags = setOf(RepositoriesTags.AUTH)
            post = PostInfo.builder {
                summary("Logout")
                description("Logout a user and invalidate refresh token")
                request {
                    description("Refresh token to invalidate")
                    requestType<UserLogoutRequest>()
                }
                response {
                    description("User successfully logged out")
                    responseType<Unit>()
                    responseCode(HttpStatusCode.OK)
                }
            }
        }

        post {
            val request = call.receive<UserLogoutRequest>()
            tokenRepository.revokeRefreshToken(request.refreshToken)
            call.respond(HttpStatusCode.OK)
        }
    }
}