package org.sportsradar.sportsradarapp.common.utils

import io.ktor.http.URLProtocol

actual val localhost: BackendConnection = BackendConnection(
    host = "10.0.2.2",
    port = 8080,
    protocol = URLProtocol.HTTP
)