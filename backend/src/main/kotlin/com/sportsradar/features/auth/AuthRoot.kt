package com.sportsradar.features.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.sportsradar.email.data.AppSecrets
import com.sportsradar.email.data.EmailService
import com.sportsradar.features.auth.data.TokenRepository
import com.sportsradar.features.auth.domain.UserSession
import com.sportsradar.features.auth.routes.checkSessionRoute
import com.sportsradar.features.auth.routes.loginRoute
import com.sportsradar.features.auth.routes.logoutRoute
import com.sportsradar.features.auth.routes.refreshTokenRoute
import com.sportsradar.features.auth.routes.signupRoute
import com.sportsradar.features.users.data.PasswordHasher
import com.sportsradar.features.users.data.UsersRepository
import com.sportsradar.jwt.JWTConfig
import com.sportsradar.jwt.JWTConfig.Companion.JWT_AUTH_ID
import com.sportsradar.jwt.JWTConfig.Companion.TOKEN_TYPE_CLAIM
import com.sportsradar.jwt.TokenType
import io.ktor.client.HttpClient
import io.ktor.client.engine.apache.Apache
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.oauth
import io.ktor.server.response.respondRedirect
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set

private fun AppSecrets.toJwtConfig(): JWTConfig = JWTConfig(
    realm = jwtRealm,
    accessSecret = jwtAccessSecret,
    refreshSecret = jwtRefreshSecret,
    audience = jwtAudience,
    issuer = jwtIssuer
)

fun Application.configureAuth(
    secrets: AppSecrets,
    usersRepository: UsersRepository,
    hasher: PasswordHasher,
    tokenRepository: TokenRepository,
    emailService: EmailService
) {
    authentication {
        oauth("auth-oauth-google") {
            urlProvider = { "http://localhost:8080/callback" }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                    requestMethod = HttpMethod.Post,
                    clientId = System.getenv("GOOGLE_CLIENT_ID"),
                    clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                    defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile")
                )
            }
            client = HttpClient(Apache)
        }
    }
    val jwtConfig = secrets.toJwtConfig()
    authentication {
        jwt(JWT_AUTH_ID) {
            realm = secrets.jwtRealm
            verifier(
                JWT.require(Algorithm.HMAC256(secrets.jwtAccessSecret))
                    .withAudience(secrets.jwtAudience)
                    .withIssuer(secrets.jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (
                    secrets.jwtAudience in credential.payload.audience &&
                    credential.payload.getClaim(TOKEN_TYPE_CLAIM)
                        .asString() == TokenType.AccessToken.name
                ) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }
    routing {
        authenticate("auth-oauth-google") {
            get("/login") {
                call.respondRedirect("/callback")
            }

            get("/callback") {
                val principal: OAuthAccessTokenResponse.OAuth2? =
                    call.authentication.principal()
                call.sessions.set(UserSession(principal?.accessToken.toString()))
                call.respondRedirect("/hello")
            }
        }
        loginRoute(
            usersRepository = usersRepository,
            hasher = hasher,
            jwtConfig = jwtConfig
        )
        logoutRoute(tokenRepository)
        refreshTokenRoute(jwtConfig)
        signupRoute(usersRepository, secrets, emailService)
        checkSessionRoute(usersRepository)
    }
}

