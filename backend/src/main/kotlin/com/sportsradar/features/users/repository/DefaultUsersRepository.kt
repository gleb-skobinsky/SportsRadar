package com.sportsradar.features.users.repository

import com.sportsradar.features.users.models.ExistingUser
import com.sportsradar.features.users.models.NewUser
import com.sportsradar.features.users.models.UpdatedUser
import com.sportsradar.shared.BaseRepository
import com.sportsradar.shared.uuid
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import java.util.UUID

object Users : IdTable<UUID>("users") {
    override val id = uuid("id").entityId().clientDefault {
        EntityID(UUID.randomUUID(), Users)
    }
    val email = varchar("email", length = 50)
    val password = varchar("password", length = 50)
    val verified = bool("verified")
    val createdAt: Column<Instant> = timestamp("created_at")
    val updatedAt: Column<Instant> = timestamp("updated_at")

    override val primaryKey = PrimaryKey(id)
}

class DefaultUsersRepository(database: Database) : UsersRepository,
    BaseRepository() {

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    override suspend fun create(user: NewUser): String = dbQuery {
        Users.insert {
            it[email] = user.email
            it[password] = user.password
            it[verified] = user.verified
            it[createdAt] = Clock.System.now()
            it[updatedAt] = Clock.System.now()
        }[Users.id].value.toString()
    }

    override suspend fun readById(id: String): ExistingUser? {
        return dbQuery {
            Users.selectAll()
                .andWhere { Users.id eq id.uuid() }
                .map { row ->
                    ExistingUser(
                        id = row[Users.id].toString(),
                        email = row[Users.email],
                        password = row[Users.password],
                        verified = row[Users.verified]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun readByEmail(email: String): ExistingUser? {
        return dbQuery {
            Users.selectAll().andWhere { Users.email eq email }
                .map { row ->
                    ExistingUser(
                        id = row[Users.id].toString(),
                        email = row[Users.email],
                        password = row[Users.password],
                        verified = row[Users.verified]
                    )
                }
                .singleOrNull()
        }
    }


    override suspend fun update(id: String, user: UpdatedUser) {
        dbQuery {
            Users.update({ Users.id eq id.uuid() }) {
                it[email] = user.email
                // it[password] = user.password
                it[updatedAt] = Clock.System.now()
            }
        }
    }

    override suspend fun delete(id: String) {
        dbQuery {
            Users.deleteWhere { Users.id eq id.uuid() }
        }
    }
}
