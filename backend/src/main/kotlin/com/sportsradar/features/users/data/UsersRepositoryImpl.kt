package com.sportsradar.features.users.data

import com.sportsradar.features.users.domain.ExistingUser
import com.sportsradar.features.users.domain.NewUser
import com.sportsradar.features.users.domain.UpdatedUser
import com.sportsradar.shared.BaseRepository
import com.sportsradar.shared.uuid
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.andWhere
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import kotlin.time.Clock

class UsersRepositoryImpl(
    database: Database,
    private val hasher: PasswordHasher,
) : UsersRepository,
    BaseRepository() {

    init {
        transaction(database) {
            SchemaUtils.create(UsersTable)
        }
    }

    override suspend fun create(user: NewUser): String = dbQuery {
        UsersTable.insert {
            it[email] = user.email
            it[password] = hasher.hash(user.password)
            it[verified] = user.verified
            it[createdAt] = Clock.System.now()
            it[updatedAt] = Clock.System.now()
        }[UsersTable.id].value.toString()
    }


    override suspend fun readById(id: String): ExistingUser? {
        return dbQuery {
            UsersTable.selectAll()
                .andWhere { UsersTable.id eq id.uuid() }
                .map { row ->
                    ExistingUser(
                        id = row[UsersTable.id].toString(),
                        email = row[UsersTable.email],
                        hashedPassword = row[UsersTable.password],
                        verified = row[UsersTable.verified],
                        firstName = row[UsersTable.firstName],
                        lastName = row[UsersTable.lastName]
                    )
                }
                .singleOrNull()
        }
    }

    override suspend fun readByEmail(email: String): ExistingUser? {
        return dbQuery {
            UsersTable.selectAll().andWhere { UsersTable.email eq email }
                .map { row ->
                    ExistingUser(
                        id = row[UsersTable.id].toString(),
                        email = row[UsersTable.email],
                        hashedPassword = row[UsersTable.password],
                        verified = row[UsersTable.verified],
                        firstName = row[UsersTable.firstName],
                        lastName = row[UsersTable.lastName]
                    )
                }
                .singleOrNull()
        }
    }


    override suspend fun update(id: String, user: UpdatedUser) {
        dbQuery {
            UsersTable.update({ UsersTable.id eq id.uuid() }) {
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[updatedAt] = Clock.System.now()
            }
        }
    }

    override suspend fun updateByEmail(email: String, user: UpdatedUser) {
        dbQuery {
            UsersTable.update({ UsersTable.email eq email }) {
                it[firstName] = user.firstName
                it[lastName] = user.lastName
                it[updatedAt] = Clock.System.now()
            }
        }
    }

    override suspend fun delete(id: String) {
        dbQuery {
            UsersTable.deleteWhere { UsersTable.id eq id.uuid() }
        }
    }
}