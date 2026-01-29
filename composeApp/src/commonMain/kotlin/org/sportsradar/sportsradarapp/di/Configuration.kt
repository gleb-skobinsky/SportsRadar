package org.sportsradar.sportsradarapp.di

import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.sportsradar.sportsradarapp.auth.authModule
import org.sportsradar.sportsradarapp.auth.data.UserSecureStorage
import org.sportsradar.sportsradarapp.auth.data.UserSecureStorageImpl
import org.sportsradar.sportsradarapp.profile.presentation.ProfileViewModel
import org.sportsradar.sportsradarapp.common.network.ApiNetworkClient
import org.sportsradar.sportsradarapp.common.network.ConnectivityStatus
import org.sportsradar.sportsradarapp.common.network.KtorClientAuthConfig
import org.sportsradar.sportsradarapp.common.network.configureKtorClient
import org.sportsradar.sportsradarapp.storage.Storage
import org.sportsradar.sportsradarapp.storage.getStorage

expect fun Scope.getConnectivityStatus(): ConnectivityStatus

private const val PREFS_CHILD_DIR = ".sportsradarapp"
internal const val MAIN_API_CLIENT_NAME = "mainApiClient"
internal const val AUTH_API_CLIENT_NAME = "authApiClient"

private val coreModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            encodeDefaults = false
            allowTrailingComma = true
        }
    }
    single<Storage> {
        getStorage(
            useSession = false,
            preferencesFileName = "sportsradarapp_base_storage.preferences_pb",
            jvmChildDirectory = PREFS_CHILD_DIR,
            json = get()
        )
    }
    single<UserSecureStorage> {
        UserSecureStorageImpl(
            get()
        )
    }
    single<ConnectivityStatus> { getConnectivityStatus() }
    single(named(MAIN_API_CLIENT_NAME)) {
        ApiNetworkClient(
            ktorClient = configureKtorClient(
                json = get(),
                authConfig = KtorClientAuthConfig.BearerAuth(
                    userSecureStorage = get(),
                    authRepository = get()
                )
            ),
            connectivityStatus = get()
        )
    }
    single(named(AUTH_API_CLIENT_NAME)) {
        ApiNetworkClient(
            ktorClient = configureKtorClient(
                json = get(),
                authConfig = KtorClientAuthConfig.NoAuth
            ),
            connectivityStatus = get()
        )
    }
    viewModel { ProfileViewModel(get()) }
}

fun KoinApplication.configureModules() {
    modules(
        coreModule,
        authModule,
    )
}