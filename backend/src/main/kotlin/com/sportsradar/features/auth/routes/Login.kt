package com.sportsradar.features.auth.routes

import com.sportsradar.features.users.data.PasswordHasher
import com.sportsradar.features.users.data.UsersRepository
import com.sportsradar.features.users.domain.toUserData
import com.sportsradar.jwt.JWTConfig
import com.sportsradar.jwt.JWTConfig.Companion.ACCESS_EXPIRATION_TIMEOUT
import com.sportsradar.jwt.JWTConfig.Companion.REFRESH_EXPIRATION_TIMEOUT
import com.sportsradar.jwt.TokenType
import com.sportsradar.shared.RepositoriesTags
import io.bkbn.kompendium.core.metadata.PostInfo
import io.bkbn.kompendium.core.plugin.NotarizedRoute
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.sportsradar.sportsradarapp.shared.auth.data.TokenData
import org.sportsradar.sportsradarapp.shared.auth.data.UserLoginRequest
import org.sportsradar.sportsradarapp.shared.auth.data.UserLoginResponse
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints

internal fun Routing.loginRoute(
    usersRepository: UsersRepository,
    hasher: PasswordHasher,
    jwtConfig: JWTConfig
) {
    route(Endpoints.Auth.Login) {
        install(NotarizedRoute()) {
            tags = setOf(RepositoriesTags.AUTH)
            post = PostInfo.builder {
                summary("Login")
                description("Login a user with his username and password")
                request {
                    description("Login a user")
                    requestType<UserLoginRequest>()
                }
                response {
                    description("User successfully logged in")
                    responseCode(HttpStatusCode.Created)
                    responseType<UserLoginResponse>()
                }
            }
        }
        post {
            val user = call.receive<UserLoginRequest>()
            val dbUser = usersRepository.readByEmail(user.email) ?: run {
                call.respond(HttpStatusCode.NotFound, "User name not found")
                return@post
            }
            if (!hasher.verify(user.password, dbUser.hashedPassword)) {
                call.respond(
                    HttpStatusCode.Unauthorized,
                    "Password is incorrect"
                )
                return@post
            }
            val accessToken = jwtConfig.createToken(
                email = user.email,
                type = TokenType.AccessToken,
                expiration = ACCESS_EXPIRATION_TIMEOUT
            )
            val refreshToken = jwtConfig.createToken(
                email = user.email,
                type = TokenType.RefreshToken,
                expiration = REFRESH_EXPIRATION_TIMEOUT
            )
            call.respond(
                UserLoginResponse(
                    tokens = TokenData(accessToken, refreshToken),
                    user = dbUser.toUserData()
                )
            )
        }
    }
}
