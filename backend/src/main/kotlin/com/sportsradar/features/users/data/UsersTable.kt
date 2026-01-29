package com.sportsradar.features.users.data

import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.Instant
import kotlin.uuid.Uuid

object UsersTable : IdTable<Uuid>("users") {

    override val id = uuid("id").entityId().clientDefault {
        EntityID(Uuid.random(), UsersTable)
    }
    val email = varchar("email", length = 50)
    val password = varchar("password", length = 100)
    val verified = bool("verified")

    val firstName = varchar("first_name", length = 100).default("")
    val lastName = varchar("last_name", length = 100).default("")
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant> = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}