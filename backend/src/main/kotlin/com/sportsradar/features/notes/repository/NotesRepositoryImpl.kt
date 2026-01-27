package com.sportsradar.features.notes.repository

import com.sportsradar.features.notes.models.NoteResponse
import com.sportsradar.features.users.data.UsersTable
import com.sportsradar.shared.BaseRepository
import com.sportsradar.shared.uuid
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder.DESC
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

private object Notes : IdTable<UUID>("notes") {
    override val id = uuid("id").entityId().clientDefault {
        EntityID(UUID.randomUUID(), Notes)
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

class NotesRepositoryImpl(
    database: Database
) : BaseRepository(), NotesRepository {

    init {
        transaction(database) {
            SchemaUtils.create(Notes)
        }
    }

    override suspend fun getNotesForUser(email: String): List<NoteResponse> {
        return dbQuery {
            val userId = getUserOrNull(
                email = email
            ) ?: return@dbQuery emptyList()
            Notes.selectAll()
                .andWhere { Notes.userId eq userId }
                .orderBy(Notes.createdAt to DESC)
                .map { row ->
                    NoteResponse(
                        id = row[Notes.id].value.toString(),
                        title = row[Notes.title],
                        body = row[Notes.content]
                    )
                }
        }
    }

    override suspend fun saveNote(
        userEmail: String,
        title: String,
        body: String
    ): Boolean {
        return dbQuery {
            val userId = getUserOrNull(
                email = userEmail
            ) ?: return@dbQuery false
            Notes.insert {
                it[this.userId] = userId
                it[this.title] = title
                it[this.content] = body
                it[createdAt] = Clock.System.now()
                it[updatedAt] = Clock.System.now()
            }
            true
        }
    }

    override suspend fun deleteNote(noteId: String): Boolean {
        return dbQuery {
            Notes.deleteWhere {
                id.eq(noteId.uuid())
            } > 0
        }
    }

    private fun getUserOrNull(email: String): UUID? {
        return UsersTable.selectAll()
            .andWhere {
                UsersTable.email eq email
            }.map { it[UsersTable.id] }
            .singleOrNull()?.value
    }
}