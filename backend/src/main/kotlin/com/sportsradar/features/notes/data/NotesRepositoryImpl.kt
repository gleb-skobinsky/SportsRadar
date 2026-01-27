package com.sportsradar.features.notes.data

import com.sportsradar.features.notes.data.dto.NoteResponse
import com.sportsradar.features.notes.data.tables.NotesTable
import com.sportsradar.features.users.data.UsersTable
import com.sportsradar.shared.BaseRepository
import com.sportsradar.shared.uuid
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class NotesRepositoryImpl(
    database: Database
) : BaseRepository(), NotesRepository {

    init {
        transaction(database) {
            SchemaUtils.create(NotesTable)
        }
    }

    override suspend fun getNotesForUser(email: String): List<NoteResponse> {
        return dbQuery {
            val userId = getUserOrNull(
                email = email
            ) ?: return@dbQuery emptyList()
            NotesTable.selectAll()
                .andWhere { NotesTable.userId eq userId }
                .orderBy(NotesTable.createdAt to SortOrder.DESC)
                .map { row ->
                    NoteResponse(
                        id = row[NotesTable.id].value.toString(),
                        title = row[NotesTable.title],
                        body = row[NotesTable.content]
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
            NotesTable.insert {
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
            NotesTable.deleteWhere {
                id eq noteId.uuid()
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