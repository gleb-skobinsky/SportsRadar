package com.sportsradar.features.users

import com.sportsradar.features.auth.data.TokenRepository
import com.sportsradar.features.auth.data.TokenRepositoryImpl
import com.sportsradar.features.users.data.PasswordHasher
import com.sportsradar.features.users.data.PasswordHasherImpl
import com.sportsradar.features.users.data.UsersRepository
import com.sportsradar.features.users.data.UsersRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val usersModule: Module = module {
    single<PasswordHasher> {
        PasswordHasherImpl()
    }
    single<UsersRepository> {
        UsersRepositoryImpl(get(), get())
    }
    single<TokenRepository> { TokenRepositoryImpl(get()) }
}