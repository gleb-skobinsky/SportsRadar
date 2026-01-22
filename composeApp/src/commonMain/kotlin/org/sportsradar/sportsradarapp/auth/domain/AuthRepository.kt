package org.sportsradar.sportsradarapp.auth.domain

import org.sportsradar.sportsradarapp.auth.domain.entities.OtpMessageType
import org.sportsradar.sportsradarapp.common.network.RequestResult

interface AuthRepository {
    suspend fun login(email: String, password: String): RequestResult<Unit>

    suspend fun register(login: String, password: String): RequestResult<Unit>

    suspend fun sendOtp(login: String): RequestResult<OtpMessageType>

    suspend fun checkOtp(login: String, code: String): RequestResult<String>

    suspend fun resetPassword(login: String, password: String, otpToken: String): RequestResult<Unit>

    suspend fun checkSession(): RequestResult<Unit>
}