package com.sportsradar.features.notes.data.tables

import com.sportsradar.features.users.data.UsersTable
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import java.util.UUID

object NotesTable : IdTable<UUID>("notes") {
    override val id = uuid("id").entityId().clientDefault {
        EntityID(UUID.randomUUID(), NotesTable)
    }
    val userId = uuid("user_id").references(
        ref = UsersTable.id,
        onDelete = ReferenceOption.CASCADE
    )
    val title = varchar("title", 255)
    val content = text("content")
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant> = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}