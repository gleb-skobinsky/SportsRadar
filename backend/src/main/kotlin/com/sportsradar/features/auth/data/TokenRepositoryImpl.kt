package com.sportsradar.features.auth.data

import com.sportsradar.features.auth.data.tables.RevokedTokensTable
import com.sportsradar.shared.BaseRepository
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.insertIgnore
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class TokenRepositoryImpl(
    database: Database
) : TokenRepository, BaseRepository() {

    override suspend fun isRevoked(token: String): Boolean = dbQuery {
        RevokedTokensTable
            .selectAll().andWhere { RevokedTokensTable.token eq token }
            .limit(1)
            .count() > 0
    }

    override suspend fun revokeRefreshToken(token: String) {
        dbQuery {
            RevokedTokensTable.insertIgnore {
                it[RevokedTokensTable.token] = token
                it[revokedAt] = System.currentTimeMillis()
            }
        }
    }

    init {
        transaction(database) {
            SchemaUtils.create(RevokedTokensTable)
        }
    }
}