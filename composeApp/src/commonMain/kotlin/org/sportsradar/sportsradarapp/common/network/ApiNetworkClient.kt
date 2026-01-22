package org.sportsradar.sportsradarapp.common.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.io.IOException
import org.jetbrains.compose.resources.getString
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.generic_eror
import org.sportsradar.sportsradarapp.resources.network_unavailable_error

class ApiNetworkClient(
    val ktorClient: HttpClient,
    private val connectivityStatus: ConnectivityStatus,
) {

    val connectivityStatusState: ConnectivityStatusState
        get() = connectivityStatus.networkState

    suspend inline fun <reified Request : Any, reified Response : Any> post(
        urlPath: String,
        body: Request? = null
    ): RequestResult<Response> {
        return wrapExceptions {
            ktorClient.post(urlPath) {
                body?.let { setBody(it) }
            }.bodyResult<Response>()
        }
    }

    suspend inline fun <reified Request : Any, reified Response : Any> put(
        urlPath: String,
        body: Request? = null
    ): RequestResult<Response> {
        return wrapExceptions {
            ktorClient.put(urlPath) {
                body?.let { setBody(it) }
            }.bodyResult<Response>()
        }
    }

    suspend inline fun <reified Response : Any> get(
        urlPath: String
    ): RequestResult<Response> {
        return wrapExceptions {
            ktorClient.get(urlPath).bodyResult<Response>()
        }
    }

    suspend inline fun <Response : Any> wrapExceptions(
        block: () -> RequestResult<Response>
    ): RequestResult<Response> {
        return try {
            block()
        } catch (e: Exception) {
            when (e) {
                is IOException -> {
                    if (connectivityStatusState.connected) {
                        RequestResult.Error(getString(AppRes.string.generic_eror))
                    } else {
                        RequestResult.Error(getString(AppRes.string.network_unavailable_error))
                    }
                }

                else -> RequestResult.Error(getString(AppRes.string.generic_eror))
            }
        }
    }

    suspend inline fun <reified Response : Any> HttpResponse.bodyResult(): RequestResult<Response> {
        return if (status.isSuccess()) {
            RequestResult.Success(data = body<Response>())
        } else {
            RequestResult.Error(
                error = ErrorData(
                    message = bodyAsText(),
                    httpStatus = status
                )
            )
        }
    }
}
