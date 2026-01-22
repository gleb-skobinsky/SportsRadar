package com.sportsradar.features.users

import com.sportsradar.features.users.repository.DefaultUsersRepository
import com.sportsradar.features.users.repository.UsersRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val usersModule: Module = module {
    single<UsersRepository> {
        DefaultUsersRepository(get())
    }
}