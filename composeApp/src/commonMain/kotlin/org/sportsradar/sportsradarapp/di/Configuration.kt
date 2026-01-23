package org.sportsradar.sportsradarapp.di

import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json
import org.koin.core.KoinApplication
import org.koin.core.scope.Scope
import org.koin.dsl.module
import org.sportsradar.sportsradarapp.auth.authModule
import org.sportsradar.sportsradarapp.auth.data.UserSecureStorage
import org.sportsradar.sportsradarapp.auth.data.UserSecureStorageImpl
import org.sportsradar.sportsradarapp.common.network.ApiNetworkClient
import org.sportsradar.sportsradarapp.common.network.ConnectivityStatus
import org.sportsradar.sportsradarapp.common.network.configureKtorClient
import org.sportsradar.sportsradarapp.storage.Storage
import org.sportsradar.sportsradarapp.storage.getStorage

expect fun Scope.getConnectivityStatus(): ConnectivityStatus

private const val PREFS_CHILD_DIR = ".sportsradarapp"

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
            getStorage(
                useSession = true,
                preferencesFileName = "sportsradarapp_session_storage.preferences_pb",
                jvmChildDirectory = PREFS_CHILD_DIR,
                json = get()
            )
        )
    }
    single<HttpClient> {
        configureKtorClient(get(), get(), get())
    }
    single<ConnectivityStatus> { getConnectivityStatus() }
    single { ApiNetworkClient(get(), get()) }
}

fun KoinApplication.configureModules() {
    modules(
        coreModule,
        authModule,
    )
}