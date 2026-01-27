package com.sportsradar.db

import com.sportsradar.email.data.AppSecrets
import com.sportsradar.shared.createDatabase
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

fun main() {
    val secrets = AppSecrets.fromEnvironment()
    val database = createDatabase(
        port = secrets.dbPort,
        dbName = secrets.dbName,
        dbUser = secrets.dbUser,
        dbPassword = secrets.dbPassword
    )
    transaction(database) {
        val statements = MigrationUtils.statementsRequiredForDatabaseMigration(
            *TablesRegistry
        )
        println("Statements: $statements")
        val dropStatements = MigrationUtils.dropUnmappedColumnsStatements(
            *TablesRegistry
        )
        println("Drop Statements: $dropStatements")
        val missingColStatements = SchemaUtils.addMissingColumnsStatements(
            *TablesRegistry
        )
        println("Missing Col Statements: $missingColStatements")
    }
}