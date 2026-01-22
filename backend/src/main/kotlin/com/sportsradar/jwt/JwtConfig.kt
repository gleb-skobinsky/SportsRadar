package com.sportsradar.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import io.ktor.server.auth.jwt.*
import java.util.*
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

private const val EMAIL_CLAIM_KEY = "email"

data class JWTConfig(
    val realm: String,
    val secret: String,
    val audience: String,
    val issuer: String
) {
    companion object {
        const val JWT_AUTH_ID = "matchme-jwt-auth"
        val ACCESS_EXPIRATION_TIMEOUT = 30.minutes
        val REFRESH_EXPIRATION_TIMEOUT = 7.days
    }
}

val JWTPrincipal.email: String
    get() = payload.getClaim(EMAIL_CLAIM_KEY).asString()

fun JWTConfig.createToken(
    email: String,
    type: TokenType,
    expiration: Duration
): String =
    JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withJWTId(type.name)
        .withClaim(EMAIL_CLAIM_KEY, email)
        .withExpiresAt(Date(System.currentTimeMillis() + expiration.inWholeMilliseconds))
        .sign(Algorithm.HMAC256(secret))

fun JWTConfig.verifyToken(token: String, type: TokenType): String? =
    try {
        val jwt = JWT.require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withJWTId(type.name)
            .withIssuer(issuer)
            .build()
            .verify(token)
        jwt.getClaim(EMAIL_CLAIM_KEY).asString()
    } catch (e: JWTVerificationException) {
        null
    }