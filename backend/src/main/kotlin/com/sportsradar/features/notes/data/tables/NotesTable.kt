package com.sportsradar.features.notes.data.tables

import com.sportsradar.features.users.data.UsersTable
import org.jetbrains.exposed.v1.core.Column
import org.jetbrains.exposed.v1.core.ReferenceOption
import org.jetbrains.exposed.v1.core.dao.id.EntityID
import org.jetbrains.exposed.v1.core.dao.id.IdTable
import org.jetbrains.exposed.v1.datetime.timestamp
import kotlin.time.Instant
import kotlin.uuid.Uuid

object NotesTable : IdTable<Uuid>("notes") {
    override val id = uuid("id").entityId().clientDefault {
        EntityID(Uuid.random(), NotesTable)
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