package com.sportsradar.features.auth.data

interface TokenRepository {
    suspend fun revokeRefreshToken(token: String)
    suspend fun isRevoked(token: String): Boolean
}