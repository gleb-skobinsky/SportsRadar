package org.sportsradar.sportsradarapp.common.network

import org.jetbrains.compose.resources.getString
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.generic_eror

suspend fun <T : Any> genericError(): RequestResult.Error<T> {
    return RequestResult.Error(getString(AppRes.string.generic_eror))
}