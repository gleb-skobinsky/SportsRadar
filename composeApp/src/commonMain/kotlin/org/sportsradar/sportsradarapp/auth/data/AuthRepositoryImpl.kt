package org.sportsradar.sportsradarapp.auth.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.jetbrains.compose.resources.getString
import org.sportsradar.sportsradarapp.auth.data.entities.CheckOtpRequest
import org.sportsradar.sportsradarapp.auth.data.entities.CheckOtpResponse
import org.sportsradar.sportsradarapp.auth.data.entities.OtpRequest
import org.sportsradar.sportsradarapp.auth.data.entities.ResetPasswordRequest
import org.sportsradar.sportsradarapp.auth.domain.AuthRepository
import org.sportsradar.sportsradarapp.auth.domain.entities.OtpMessageType
import org.sportsradar.sportsradarapp.common.network.ApiNetworkClient
import org.sportsradar.sportsradarapp.common.network.RequestResult
import org.sportsradar.sportsradarapp.common.network.ServerResponse
import org.sportsradar.sportsradarapp.common.network.mapOnError
import org.sportsradar.sportsradarapp.common.network.mapOnSuccess
import org.sportsradar.sportsradarapp.common.network.onError
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.email
import org.sportsradar.sportsradarapp.resources.failed_to_send_otp
import org.sportsradar.sportsradarapp.resources.generic_eror
import org.sportsradar.sportsradarapp.resources.otp_login_not_found
import org.sportsradar.sportsradarapp.resources.otp_too_many_times
import org.sportsradar.sportsradarapp.resources.user_already_exists
import org.sportsradar.sportsradarapp.resources.wrong_otp_code
import org.sportsradar.sportsradarapp.shared.auth.data.RefreshTokenRequest
import org.sportsradar.sportsradarapp.shared.auth.data.SignupRequest
import org.sportsradar.sportsradarapp.shared.auth.data.SignupResponse
import org.sportsradar.sportsradarapp.shared.auth.data.TokenData
import org.sportsradar.sportsradarapp.shared.auth.data.UserData
import org.sportsradar.sportsradarapp.shared.auth.data.UserLoginRequest
import org.sportsradar.sportsradarapp.shared.auth.data.UserLoginResponse
import org.sportsradar.sportsradarapp.shared.auth.data.UserLogoutRequest
import org.sportsradar.sportsradarapp.shared.auth.domain.Tokens
import org.sportsradar.sportsradarapp.shared.auth.domain.User
import org.sportsradar.sportsradarapp.shared.auth.domain.toTokens
import org.sportsradar.sportsradarapp.shared.auth.domain.toUser
import org.sportsradar.sportsradarapp.shared.common.data.Endpoints

private const val USER_ALREADY_EXIST_CONTRACT = "User already exists"
private const val SMS_CONTRACT = "phone_message_sent"
private const val EMAIL_CONTRACT = "email_message_sent"
private const val OTP_LOGIN_CONTRACT = "auc_error_used_login"
private const val OTP_TOO_MANY_TIMES_CONTRACT = "too_many_tries"
private const val FAILED_TO_SEND_OTP_CONTRACT = "auc_error_send_otp"
private const val OTP_LOGIN_NOT_FOUND = "auc_tu_not_found"
private const val OTP_WRONG_CODE_CONTRACT = "wrong_code"
private const val USER_NOT_FOUND_CONTRACT = "auc_user_not_found"
private const val OTP_VAlIDATION_ERROR_CONTRACT = "auc_error_user_params"
private const val OTP_TOKEN_EXPIRED_CONTRACT = "session_is_over"

class AuthRepositoryImpl(
    private val client: ApiNetworkClient,
    private val userSecureStorage: UserSecureStorage
) : AuthRepository {

    private val mutex = Mutex()

    override fun subscribeToUserData(): Flow<User?> {
        return userSecureStorage.getUserData().distinctUntilChanged()
    }

    override suspend fun logout(): RequestResult<Unit> {
        val refreshToken = userSecureStorage.getRefreshToken() ?: run {
            userSecureStorage.clearAll()
            return RequestResult.Success(Unit)
        }
        return client.post<UserLogoutRequest, Unit>(
            urlPath = Endpoints.Auth.Logout,
            body = UserLogoutRequest(refreshToken)
        ).mapOnSuccess {
            userSecureStorage.clearAll()
            RequestResult.Success(Unit)
        }
    }

    override suspend fun login(
        email: String,
        password: String
    ): RequestResult<Tokens> {
        return client.post<UserLoginRequest, UserLoginResponse>(
            urlPath = Endpoints.Auth.Login,
            body = UserLoginRequest(email = email, password = password)
        ).mapOnSuccess { loginData ->
            val domainUser = loginData.user.toUser()
            userSecureStorage.saveTokens(loginData.tokens)
            userSecureStorage.saveUserData(domainUser)
            RequestResult.Success(loginData.tokens.toTokens())
        }
    }

    override suspend fun refresh(): RequestResult<Tokens> {
        return mutex.withLock {
            val refreshToken = userSecureStorage.getRefreshToken() ?: return RequestResult.Error("")
            client.post<RefreshTokenRequest, TokenData>(
                urlPath = Endpoints.Auth.RefreshToken,
                body = RefreshTokenRequest(refreshToken = refreshToken)
            ).mapOnSuccess { tokens ->
                userSecureStorage.saveTokens(tokens)
                RequestResult.Success(tokens.toTokens())
            }.onError {
                userSecureStorage.clearAll()
            }
        }
    }

    override suspend fun register(
        login: String,
        password: String
    ): RequestResult<Unit> {
        return client.post<SignupRequest, SignupResponse>(
            urlPath = Endpoints.Auth.Signup,
            body = SignupRequest(
                email = login,
                password = password
            )
        ).mapOnError {
            when {
                it.message == USER_ALREADY_EXIST_CONTRACT -> {
                    RequestResult.Error(getString(AppRes.string.user_already_exists))
                }

                else -> RequestResult.Error(getString(AppRes.string.generic_eror))
            }
        }.mapOnSuccess {
            RequestResult.Success(Unit)
        }
    }

    override suspend fun sendOtp(login: String): RequestResult<OtpMessageType> {
        return client.post<OtpRequest, ServerResponse>(
            "/onboarding/api/auth/2fa/send",
            OtpRequest(login)
        )
            .mapOnSuccess {
                when {
                    !it.error && it.message == EMAIL_CONTRACT -> RequestResult.Success(
                        OtpMessageType.EMAIL
                    )

                    !it.error && it.message == SMS_CONTRACT -> RequestResult.Success(
                        OtpMessageType.SMS
                    )

                    it.error && it.message == OTP_LOGIN_CONTRACT -> RequestResult.Error(
                        getString(
                            AppRes.string.otp_login_not_found
                        )
                    )

                    it.error && it.message == OTP_TOO_MANY_TIMES_CONTRACT -> RequestResult.Error(
                        getString(AppRes.string.otp_too_many_times)
                    )

                    it.error && it.message == FAILED_TO_SEND_OTP_CONTRACT -> RequestResult.Error(
                        getString(AppRes.string.failed_to_send_otp)
                    )

                    else -> RequestResult.Error(getString(AppRes.string.generic_eror))
                }
            }
    }

    override suspend fun checkOtp(
        login: String,
        code: String
    ): RequestResult<String> {
        return client.post<CheckOtpRequest, CheckOtpResponse>(
            urlPath = "/onboarding/api/auth/2fa/check",
            body = CheckOtpRequest(login, code)
        ).mapOnSuccess { result ->
            when {
                result.error && result.message == OTP_LOGIN_NOT_FOUND -> RequestResult.Error(
                    getString(AppRes.string.generic_eror)
                )

                result.error && result.message == OTP_LOGIN_CONTRACT -> RequestResult.Error(
                    getString(AppRes.string.otp_login_not_found)
                )

                result.error && result.message == OTP_TOO_MANY_TIMES_CONTRACT -> RequestResult.Error(
                    getString(AppRes.string.otp_too_many_times)
                )

                result.error && result.message == OTP_WRONG_CODE_CONTRACT -> RequestResult.Error(
                    getString(AppRes.string.wrong_otp_code)
                )

                !result.error -> {
                    result.token?.let { token ->
                        RequestResult.Success(token)
                    }
                        ?: RequestResult.Error(getString(AppRes.string.generic_eror))
                }

                else -> RequestResult.Error(getString(AppRes.string.generic_eror))
            }
        }
    }

    override suspend fun resetPassword(
        login: String,
        password: String,
        otpToken: String
    ): RequestResult<Unit> {
        return client.post<ResetPasswordRequest, ServerResponse>(
            urlPath = "/onboarding/api/auth/reset/password",
            body = ResetPasswordRequest(login, password, otpToken)
        ).mapOnSuccess {
            when {
                it.error && it.message == USER_NOT_FOUND_CONTRACT -> {
                    RequestResult.Error(getString(AppRes.string.otp_login_not_found))
                }

                it.error && it.message == OTP_VAlIDATION_ERROR_CONTRACT -> {
                    RequestResult.Error(getString(AppRes.string.email))
                }

                it.error && it.message == OTP_TOKEN_EXPIRED_CONTRACT -> {
                    RequestResult.Error(getString(AppRes.string.email))
                }

                !it.error -> {
                    RequestResult.Success(Unit)
                }

                else -> RequestResult.Error(getString(AppRes.string.generic_eror))
            }
        }
    }
}