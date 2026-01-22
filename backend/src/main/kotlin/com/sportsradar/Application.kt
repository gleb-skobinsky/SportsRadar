package com.sportsradar

import com.sportsradar.di.configureKoin
import com.sportsradar.email.data.AppSecrets
import com.sportsradar.features.auth.configureAuth
import com.sportsradar.features.notes.configureNotes
import com.sportsradar.features.users.configureUsersRoutes
import com.sportsradar.plugins.configureMonitoring
import com.sportsradar.plugins.configureSerialization
import com.sportsradar.plugins.configureSockets
import com.sportsradar.plugins.configureStaticFiles
import com.sportsradar.plugins.configureSwagger
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.cors.routing.CORS
import org.koin.ktor.ext.get

fun main() {
    embeddedServer(
        factory = Netty,
        port = 8080,
        module = Application::module
    ).start(true)
}

fun Application.module() {
    val secrets = AppSecrets.fromEnvironment()
    configureCors()
    configureKoin(secrets)
    configureSwagger(secrets)
    configureSockets()
    configureSerialization()
    configureAuth(secrets, get(), get())
    configureNotes(get())
    configureUsersRoutes(get())
    configureMonitoring()
    configureStaticFiles()
}

private fun Application.configureCors() {
    install(CORS) {
        allowHost("localhost:8082", schemes = listOf("http"))

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)
        allowCredentials = true
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Options)
    }
}
