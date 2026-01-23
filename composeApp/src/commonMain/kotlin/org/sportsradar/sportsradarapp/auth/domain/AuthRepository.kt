package org.sportsradar.sportsradarapp.auth.domain

import kotlinx.coroutines.flow.Flow
import org.sportsradar.sportsradarapp.auth.domain.entities.OtpMessageType
import org.sportsradar.sportsradarapp.common.network.RequestResult
import org.sportsradar.sportsradarapp.shared.auth.domain.Tokens
import org.sportsradar.sportsradarapp.shared.auth.domain.User

interface AuthRepository {
    fun subscribeToUserData(): Flow<User?>

    suspend fun login(email: String, password: String): RequestResult<Tokens>

    suspend fun register(login: String, password: String): RequestResult<Unit>

    suspend fun sendOtp(login: String): RequestResult<OtpMessageType>

    suspend fun checkOtp(login: String, code: String): RequestResult<String>

    suspend fun resetPassword(login: String, password: String, otpToken: String): RequestResult<Unit>

    suspend fun checkSession(): RequestResult<Unit>

    suspend fun refresh(): RequestResult<Tokens>

    suspend fun logout(): RequestResult<Unit>
}