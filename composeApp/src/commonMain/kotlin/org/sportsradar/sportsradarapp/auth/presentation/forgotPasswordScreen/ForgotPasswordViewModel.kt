package org.sportsradar.sportsradarapp.auth.presentation.forgotPasswordScreen

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.inject
import org.sportsradar.sportsradarapp.auth.domain.AuthRepository
import org.sportsradar.sportsradarapp.auth.domain.entities.OtpMessageType
import org.sportsradar.sportsradarapp.common.mvi.BaseAction
import org.sportsradar.sportsradarapp.common.mvi.BaseEffect
import org.sportsradar.sportsradarapp.common.mvi.BaseState
import org.sportsradar.sportsradarapp.common.mvi.BaseViewModel
import org.sportsradar.sportsradarapp.common.network.onError
import org.sportsradar.sportsradarapp.common.network.onSuccess
import org.sportsradar.sportsradarapp.common.utils.mapDistinctBy
import org.sportsradar.sportsradarapp.common.utils.onlyLatin
import org.sportsradar.sportsradarapp.common.utils.passwordsMatch
import org.sportsradar.sportsradarapp.common.utils.satisfiesMinLength
import org.sportsradar.sportsradarapp.common.utils.upperCaseNumberOrSpecialSymbol

class ForgotPasswordViewModel(
    email: String?,
) :
    BaseViewModel<ForgotPasswordState, ForgotPasswordAction, ForgotPasswordEffect>(
        ForgotPasswordState(
            email = email.orEmpty()
        )
    ) {

    private val authRepository: AuthRepository by inject()

    override suspend fun performOnAction(action: ForgotPasswordAction) {
        when (action) {
            is ForgotPasswordAction.UpdateCurrentPassword -> setState { it.copy(currentPassword = action.value) }
            is ForgotPasswordAction.UpdateNewPassword -> setState { it.copy(password = action.value) }
            is ForgotPasswordAction.UpdateRepeatPassword -> setState { it.copy(repeatPassword = action.value) }
            is ForgotPasswordAction.UpdateEmail -> setState { it.copy(email = action.value) }
            ForgotPasswordAction.SendOtp -> {
                setState { it.copy(isLoading = true) }
                authRepository.sendOtp(currentState.email)
                    .onSuccess { type ->
                        setState { it.copy(otpType = type, otpCode = "") }
                        setEffect(ForgotPasswordEffect.ShowBottomSheet)
                    }
                    .onError { error ->
                        setEffect(ForgotPasswordEffect.ShowError(error.message))
                    }
                setState { it.copy(isLoading = false) }
            }

            is ForgotPasswordAction.UpdateOtpCode -> setState { it.copy(otpCode = action.value) }
            ForgotPasswordAction.CheckOtp -> {
                authRepository.checkOtp(currentState.email, currentState.otpCode)
                    .onSuccess { token ->
                        setEffect(ForgotPasswordEffect.HideBottomSheet)
                        authRepository.resetPassword(
                            currentState.email,
                            currentState.password,
                            token
                        )
                            .onSuccess {
                                setEffect(ForgotPasswordEffect.Success)
                            }
                    }
                    .onError { error ->
                        setEffect(ForgotPasswordEffect.HideBottomSheet)
                        setEffect(ForgotPasswordEffect.ShowError(error.message))
                    }

            }

        }
    }

    init {
        viewModelScope.launch {
            state
                .mapDistinctBy { it.password + it.repeatPassword }
                .collectLatest {
                    setState {
                        it.copy(
                            passwordsMatch = (it.password to it.repeatPassword).passwordsMatch(),
                            onlyLatin = it.password.onlyLatin(),
                            longEnough = it.password.satisfiesMinLength(8),
                            specialSymbols = it.password.upperCaseNumberOrSpecialSymbol()
                        )
                    }
                }
        }
    }
}

data class ForgotPasswordState(
    val email: String = "",
    val currentPassword: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    val passwordsMatch: Boolean = false,
    val onlyLatin: Boolean = false,
    val longEnough: Boolean = false,
    val specialSymbols: Boolean = false,
    val isLoading: Boolean = false,
    val otpType: OtpMessageType = OtpMessageType.EMAIL,
    val otpCode: String = ""
) : BaseState {
    val canResetPassword = passwordsMatch && onlyLatin && longEnough && specialSymbols
}

sealed interface ForgotPasswordAction : BaseAction {
    data class UpdateEmail(val value: String) : ForgotPasswordAction
    data class UpdateCurrentPassword(val value: String) : ForgotPasswordAction
    data class UpdateNewPassword(val value: String) : ForgotPasswordAction
    data class UpdateRepeatPassword(val value: String) : ForgotPasswordAction
    data object SendOtp : ForgotPasswordAction
    data class UpdateOtpCode(val value: String) : ForgotPasswordAction
    data object CheckOtp : ForgotPasswordAction
}

sealed interface ForgotPasswordEffect : BaseEffect {
    data class ShowError(val message: String) : ForgotPasswordEffect
    data object ShowBottomSheet : ForgotPasswordEffect
    data object HideBottomSheet : ForgotPasswordEffect
    data object Success : ForgotPasswordEffect
}

