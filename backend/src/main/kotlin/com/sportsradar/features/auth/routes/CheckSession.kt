package com.sportsradar.features.auth.routes

import org.sportsradar.sportsradarapp.shared.auth.data.UserData
import com.sportsradar.features.users.models.toUserData
import com.sportsradar.features.users.repository.UsersRepository
import com.sportsradar.jwt.JWTConfig
import com.sportsradar.jwt.email
import com.sportsradar.shared.RepositoriesTags
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints
import io.bkbn.kompendium.core.metadata.GetInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

internal fun Routing.checkSessionRoute(
    usersRepository: UsersRepository
) {
    authenticate(JWTConfig.JWT_AUTH_ID) {
        route(Endpoints.Auth.CheckSession) {
            install(NotarizedRoute()) {
                tags = setOf(RepositoriesTags.AUTH)
                get = GetInfo.builder {
                    summary("Session check")
                    description("Check if the user's session is still valid")
                    response {
                        description("User's session is valid")
                        responseCode(HttpStatusCode.Created)
                        responseType<UserData>()
                    }
                }
            }
            get {
                val email = call.principal<JWTPrincipal>()?.email ?: run {
                    call.respond(
                        HttpStatusCode.Unauthorized,
                        "Request authentication failed"
                    )
                    return@get
                }
                val dbUser = usersRepository.readByEmail(email) ?: run {
                    call.respond(HttpStatusCode.NotFound, "User not found")
                    return@get
                }

                call.respond(
                    dbUser.toUserData()
                )
            }
        }
    }
}