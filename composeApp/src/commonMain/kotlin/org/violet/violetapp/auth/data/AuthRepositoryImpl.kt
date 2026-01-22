package org.violet.violetapp.auth.data

import org.violet.violetapp.shared.auth.data.SignupRequest
import org.violet.violetapp.shared.auth.data.SignupResponse
import org.violet.violetapp.shared.auth.data.TokenData
import org.violet.violetapp.shared.auth.data.UserData
import org.violet.violetapp.shared.auth.data.UserLoginRequest
import org.violet.violetapp.shared.common.data.Endpoints
import org.jetbrains.compose.resources.getString
import org.violet.violetapp.auth.data.entities.CheckOtpRequest
import org.violet.violetapp.auth.data.entities.CheckOtpResponse
import org.violet.violetapp.auth.data.entities.OtpRequest
import org.violet.violetapp.auth.data.entities.ResetPasswordRequest
import org.violet.violetapp.auth.domain.AuthRepository
import org.violet.violetapp.auth.domain.entities.OtpMessageType
import org.violet.violetapp.common.network.ApiNetworkClient
import org.violet.violetapp.common.network.RequestResult
import org.violet.violetapp.common.network.ServerResponse
import org.violet.violetapp.common.network.mapOnError
import org.violet.violetapp.common.network.mapOnSuccess
import org.violet.violetapp.resources.AppRes
import org.violet.violetapp.resources.email
import org.violet.violetapp.resources.failed_to_send_otp
import org.violet.violetapp.resources.generic_eror
import org.violet.violetapp.resources.otp_login_not_found
import org.violet.violetapp.resources.otp_too_many_times
import org.violet.violetapp.resources.user_already_exists
import org.violet.violetapp.resources.wrong_otp_code

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
    override suspend fun login(
        email: String,
        password: String
    ): RequestResult<Unit> =
        client.post<UserLoginRequest, TokenData>(
            urlPath = Endpoints.Auth.Login,
            body = UserLoginRequest(email = email, password = password)
        ).mapOnSuccess {
            userSecureStorage.saveToken(it.accessToken)
            RequestResult.Success(Unit)
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
        )
            .mapOnSuccess { result ->
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

    override suspend fun checkSession(): RequestResult<Unit> {
        return client.get<UserData>(
            urlPath = Endpoints.Auth.CheckSession
        ).mapOnSuccess {
            RequestResult.Success(Unit)
        }
    }
}