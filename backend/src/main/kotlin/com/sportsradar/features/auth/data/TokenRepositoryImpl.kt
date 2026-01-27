package com.sportsradar.features.auth.data

import com.sportsradar.shared.BaseRepository
import org.jetbrains.exposed.sql.Database

class TokenRepositoryImpl(database: Database) : TokenRepository, BaseRepository() {
    // TODO: Revoke in DB
    private val revoked = mutableSetOf<String>()

    override suspend fun isRevoked(token: String): Boolean {
        return token in revoked
    }

    override suspend fun revokeRefreshToken(token: String) {
        revoked.add(token)
    }
}