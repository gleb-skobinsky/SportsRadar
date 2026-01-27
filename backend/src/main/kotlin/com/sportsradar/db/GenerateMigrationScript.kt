package com.sportsradar.db

import com.sportsradar.email.data.AppSecrets
import com.sportsradar.shared.createDatabase
import org.jetbrains.exposed.v1.core.ExperimentalDatabaseMigrationApi
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.migration.jdbc.MigrationUtils

internal const val MIGRATIONS_DIRECTORY = "backend/src/main/kotlin/com/sportsradar/db/migrations"

fun main() {
    val secrets = AppSecrets.fromEnvironment()
    val database = createDatabase(
        port = secrets.dbPort,
        dbName = secrets.dbName,
        dbUser = secrets.dbUser,
        dbPassword = secrets.dbPassword
    )
    transaction(database) {
        generateMigrationScript()
    }
}

@OptIn(ExperimentalDatabaseMigrationApi::class)
private fun generateMigrationScript() {
    MigrationUtils.generateMigrationScript(
        *TablesRegistry,
        scriptDirectory = MIGRATIONS_DIRECTORY,
        scriptName = MigrationsRegistry.last(),
    )
}