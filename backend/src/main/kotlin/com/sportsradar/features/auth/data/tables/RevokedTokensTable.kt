package com.sportsradar.features.auth.data.tables

import org.jetbrains.exposed.v1.core.Table

object RevokedTokensTable : Table("revoked_tokens") {
    val token = varchar("token", 512)
    val revokedAt = long("revoked_at")

    override val primaryKey = PrimaryKey(token)
}