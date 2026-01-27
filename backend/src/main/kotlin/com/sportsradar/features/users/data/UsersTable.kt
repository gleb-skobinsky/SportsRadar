package com.sportsradar.features.users.data

import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID

object UsersTable : IdTable<UUID>("users") {
    override val id = uuid("id").entityId().clientDefault {
        EntityID(UUID.randomUUID(), UsersTable)
    }
    val email = varchar("email", length = 50)
    val password = varchar("password", length = 50)
    val verified = bool("verified")
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant> = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}