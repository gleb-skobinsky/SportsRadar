package org.sportsradar.sportsradarapp.common.utils

import io.ktor.http.URLProtocol

data class BackendConnection(
    val host: String,
    val port: Int,
    val protocol: URLProtocol
) {
    companion object {
        val SimpleLocalHost = BackendConnection(
            host = "localhost",
            port = 8080,
            protocol = URLProtocol.HTTP
        )
    }
}

expect val localhost: BackendConnection
