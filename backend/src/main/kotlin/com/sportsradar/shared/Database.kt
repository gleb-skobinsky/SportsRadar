package com.sportsradar.shared

import com.sportsradar.email.data.AppSecrets
import org.jetbrains.exposed.v1.jdbc.Database
import org.koin.dsl.module

val databaseModule = module {
    single<Database> {
        val secrets: AppSecrets = get()
        createDatabase(
            port = secrets.dbPort,
            dbName = secrets.dbName,
            dbUser = secrets.dbUser,
            dbPassword = secrets.dbPassword
        )
    }
}

internal fun createDatabase(
    port: Int,
    dbName: String,
    dbUser: String,
    dbPassword: String,
): Database {
    return Database.connect(
        url = getDatabaseUrl(port, dbName),
        user = dbUser,
        driver = "org.h2.Driver",
        password = dbPassword,
    )
}

internal fun getDatabaseUrl(port: Int, dbName: String): String {
    return "jdbc:postgresql://localhost:$port/$dbName"
}