package com.sportsradar.di

import com.sportsradar.email.data.AppSecrets
import com.sportsradar.email.emailKoinModule
import com.sportsradar.features.notes.notesModule
import com.sportsradar.features.users.usersModule
import com.sportsradar.shared.databaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.module

internal fun configureKoin(secrets: AppSecrets) {
    startKoin {
        modules(
            module {
                single<AppSecrets> { secrets }
            },
            databaseModule,
            emailKoinModule,
            usersModule,
            notesModule
        )
    }
}