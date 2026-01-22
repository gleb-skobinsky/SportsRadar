package com.sportsradar.shared

import com.sportsradar.email.data.AppSecrets
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

val databaseModule = module {
    single<Database> {
        val secrets: AppSecrets = get()
        Database.connect(
            url = "jdbc:postgresql://localhost:${secrets.dbPort}/${secrets.dbName}",
            user = secrets.dbUser,
            driver = "org.h2.Driver",
            password = secrets.dbPassword
        )
    }
}