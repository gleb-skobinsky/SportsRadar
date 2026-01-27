package com.sportsradar.features.users

import com.sportsradar.features.users.data.UsersRepositoryImpl
import com.sportsradar.features.auth.data.TokenRepository
import com.sportsradar.features.auth.data.TokenRepositoryImpl
import com.sportsradar.features.users.data.UsersRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val usersModule: Module = module {
    single<UsersRepository> {
        UsersRepositoryImpl(get())
    }
    single<TokenRepository> { TokenRepositoryImpl(get()) }
}