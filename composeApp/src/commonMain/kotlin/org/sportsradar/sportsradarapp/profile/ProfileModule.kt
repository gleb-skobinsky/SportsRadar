package org.sportsradar.sportsradarapp.profile

import org.koin.dsl.module
import org.sportsradar.sportsradarapp.profile.data.ProfileRepository
import org.sportsradar.sportsradarapp.profile.data.ProfileRepositoryImpl

internal val profileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
}