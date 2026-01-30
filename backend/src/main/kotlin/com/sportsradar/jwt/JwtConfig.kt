package com.sportsradar.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import java.util.Date
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

private const val EMAIL_CLAIM_KEY = "email"

data class JWTConfig(
    val realm: String,
    val accessSecret: String,
    val refreshSecret: String,
    val audience: String,
    val issuer: String
) {
    fun createToken(
        email: String,
        type: TokenType,
        expiration: Duration
    ): String {
        val secret = type.toSecret()
        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withJWTId(Uuid.random().toString())
            .withClaim(EMAIL_CLAIM_KEY, email)
            .withClaim(TOKEN_TYPE_CLAIM, type.name)
            .withExpiresAt(Date(System.currentTimeMillis() + expiration.inWholeMilliseconds))
            .sign(Algorithm.HMAC256(secret))
    }

    fun verifyToken(token: String, type: TokenType): String? = try {
        val secret = type.toSecret()
        val jwt = JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()
            .verify(token)
        if (jwt.getClaim(TOKEN_TYPE_CLAIM).asString() != type.name) {
            return null
        }
        jwt.getClaim(EMAIL_CLAIM_KEY).asString()
    } catch (_: JWTVerificationException) {
        null
    }

    private fun TokenType.toSecret() = when (this) {
        TokenType.RefreshToken -> refreshSecret
        TokenType.AccessToken -> accessSecret
    }

    companion object {
        const val JWT_AUTH_ID = "matchme-jwt-auth"
        val ACCESS_EXPIRATION_TIMEOUT = 30.minutes
        val REFRESH_EXPIRATION_TIMEOUT = 7.days
        const val TOKEN_TYPE_CLAIM = "type"
    }
}

fun ApplicationCall.emailByAuth(): String? = principal<JWTPrincipal>()
    ?.payload
    ?.getClaim(EMAIL_CLAIM_KEY)
    ?.asString()