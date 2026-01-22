package org.sportsradar.sportsradarapp.common.network

import io.ktor.http.HttpStatusCode

sealed class RequestResult<T : Any>(
    open val data: T?,
    open val error: ErrorData?
) {

    data class Success<T : Any>(override val data: T) :
        RequestResult<T>(data = data, error = null)

    data class Error<T : Any>(override val error: ErrorData) :
        RequestResult<T>(data = null, error = error) {
        constructor(error: String) : this(ErrorData(error))
    }
}

data class ErrorData(
    val message: String,
    val httpStatus: HttpStatusCode? = null
)