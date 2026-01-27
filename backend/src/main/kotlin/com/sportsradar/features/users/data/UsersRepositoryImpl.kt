package com.sportsradar.features.users.data

import com.sportsradar.features.users.domain.ExistingUser
import com.sportsradar.features.users.domain.NewUser
import com.sportsradar.features.users.domain.UpdatedUser
import com.sportsradar.shared.BaseRepository
import com.sportsradar.shared.uuid
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class UsersRepositoryImpl(database: Database) : UsersRepository,
    BaseRepository() {

    init {
        transaction(database) {
            SchemaUtils.create(UsersTable)
        }
    }

    override suspend fun create(user: NewUser): String = dbQuery {
        UsersTable.insert {
            it[email] = user.email
            it[password] = user.password
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
                        password = row[UsersTable.password],
                        verified = row[UsersTable.verified]
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
                        password = row[UsersTable.password],
                        verified = row[UsersTable.verified]
                    )
                }
                .singleOrNull()
        }
    }


    override suspend fun update(id: String, user: UpdatedUser) {
        dbQuery {
            UsersTable.update({ UsersTable.id eq id.uuid() }) {
                it[email] = user.email
                // it[password] = user.password
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