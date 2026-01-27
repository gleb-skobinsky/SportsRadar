package com.sportsradar.db

import com.sportsradar.email.data.AppSecrets
import com.sportsradar.shared.getDatabaseUrl
import io.ktor.server.application.Application
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.koin.ktor.ext.get

internal fun Application.applyDatabaseMigrations(
    secrets: AppSecrets,
) {
    val db = get<Database>()
    val flyway = Flyway.configure()
        .dataSource(
            getDatabaseUrl(secrets.dbPort, secrets.dbName),
            secrets.dbUser,
            secrets.dbPassword
        )
        .locations("filesystem:$MIGRATIONS_DIRECTORY")
        .baselineOnMigrate(true)
        .load()
    transaction(db) {
        flyway.migrate()
    }
}