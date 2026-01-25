package org.sportsradar.sportsradarapp.auth.presentation.forgotPasswordScreen

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.sportsradar.sportsradarapp.auth.domain.entities.OtpMessageType
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.BigAuthText
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.SportsRadarAppLogo
import org.sportsradar.sportsradarapp.auth.presentation.signupScreen.PasswordConditions
import org.sportsradar.sportsradarapp.common.icons.Lock
import org.sportsradar.sportsradarapp.common.icons.LockHidden
import org.sportsradar.sportsradarapp.common.icons.Message
import org.sportsradar.sportsradarapp.common.icons.Sms
import org.sportsradar.sportsradarapp.common.mvi.CollectEffects
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.presentation.RootSnackbarController
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppBottomSheetScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppBottomSheetState
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppButton
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppPasswordTextField
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppTextField
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer
import org.sportsradar.sportsradarapp.common.presentation.components.rememberSportsRadarAppBottomSheetState
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.email
import org.sportsradar.sportsradarapp.resources.enter_otp_email
import org.sportsradar.sportsradarapp.resources.enter_otp_sms
import org.sportsradar.sportsradarapp.resources.make_up_new_password
import org.sportsradar.sportsradarapp.resources.otp_code_placeholder
import org.sportsradar.sportsradarapp.resources.password_restoration
import org.sportsradar.sportsradarapp.resources.repeat_password
import org.sportsradar.sportsradarapp.resources.restore_password
import org.sportsradar.sportsradarapp.resources.send_otp_code
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
fun ForgotPasswordScreen(
    email: String? = null,
) {
    val viewModel: ForgotPasswordViewModel = koinViewModel {
        parametersOf(email)
    }

    val focusManager = LocalFocusManager.current
    val navigator = LocalKmpNavigator.current
    val bottomSheetState = rememberSportsRadarAppBottomSheetState()
    viewModel.CollectEffects { effect ->
        when (effect) {
            is ForgotPasswordEffect.ShowError -> RootSnackbarController.showSnackbar(effect.message)

            ForgotPasswordEffect.ShowBottomSheet -> {
                focusManager.clearFocus(true)
                bottomSheetState.expand()
            }

            ForgotPasswordEffect.Success -> {
                navigator.replaceAll(Screens.LoginScreen)
            }

            ForgotPasswordEffect.HideBottomSheet -> {
                focusManager.clearFocus(true)
                bottomSheetState.hide()
            }
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    ForgotPasswordScreenContent(
        state = state,
        onAction = viewModel::onAction,
        bottomSheetState = bottomSheetState,
        hasEmail = email != null
    )
}

@Composable
fun ForgotPasswordScreenContent(
    state: ForgotPasswordState,
    onAction: (ForgotPasswordAction) -> Unit,
    bottomSheetState: SportsRadarAppBottomSheetState,
    hasEmail: Boolean = false
) {
    SportsRadarAppBottomSheetScaffold(
        sheetState = bottomSheetState,
        sheetHeightFraction = .5f,
        bottomSheetContent = {
            OtpSheetContent(state.otpType, state.otpCode, onAction)
        },
        bottomSheetColor = LocalSportsRadarTheme.colors.surface
    ) {
        22.dp.VerticalSpacer()
        SportsRadarAppLogo()
        50.dp.VerticalSpacer()
        BigAuthText(stringResource(AppRes.string.password_restoration))
        40.dp.VerticalSpacer()
        if (!hasEmail) {
            SportsRadarAppTextField(
                value = state.email,
                onValueChange = {
                    onAction(ForgotPasswordAction.UpdateEmail(it))
                },
                leftIcon = Sms,
                placeholder = stringResource(AppRes.string.email)
            )
            24.dp.VerticalSpacer()
        }
        SportsRadarAppPasswordTextField(
            value = state.password,
            onValueChange = {
                onAction(ForgotPasswordAction.UpdateNewPassword(it))
            },
            leftIcon = Lock,
            placeholder = stringResource(AppRes.string.make_up_new_password)
        )
        24.dp.VerticalSpacer()
        SportsRadarAppPasswordTextField(
            value = state.repeatPassword,
            onValueChange = {
                onAction(ForgotPasswordAction.UpdateRepeatPassword(it))
            },
            leftIcon = LockHidden,
            placeholder = stringResource(AppRes.string.repeat_password)
        )
        24.dp.VerticalSpacer()
        PasswordConditions(
            passwordsMatch = state.passwordsMatch,
            containOnlyLatin = state.onlyLatin,
            isLongEnough = state.longEnough,
            hasSpecialSymbols = state.specialSymbols
        )
        40.dp.VerticalSpacer()
        SportsRadarAppButton(
            label = stringResource(AppRes.string.restore_password),
            modifier = Modifier.fillMaxWidth(),
            enabled = state.canResetPassword,
            isLoading = state.isLoading
        ) {
            onAction(ForgotPasswordAction.SendOtp)
        }
        24.dp.VerticalSpacer()
    }
}

@Composable
private fun ColumnScope.OtpSheetContent(
    type: OtpMessageType,
    otpCode: String,
    onAction: (ForgotPasswordAction) -> Unit,
) {
    Text(
        text = stringResource(
            when (type) {
                OtpMessageType.EMAIL -> AppRes.string.enter_otp_email
                OtpMessageType.SMS -> AppRes.string.enter_otp_sms
            }
        ),
        modifier = Modifier.align(Alignment.CenterHorizontally),
        style = LocalSportsRadarTheme.typography.bodyLarge,
        color = LocalSportsRadarTheme.colors.primaryContainer
    )
    24.dp.VerticalSpacer()
    SportsRadarAppTextField(
        value = otpCode,
        placeholder = stringResource(AppRes.string.otp_code_placeholder),
        onValueChange = { newVal ->
            onAction(ForgotPasswordAction.UpdateOtpCode(newVal))
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textColor = LocalSportsRadarTheme.colors.primaryContainer,
        placeholderColor = LocalSportsRadarTheme.colors.secondaryContainer,
        backgroundColor = LocalSportsRadarTheme.colors.tertiary,
        leftIcon = Message
    )
    40.dp.VerticalSpacer()
    SportsRadarAppButton(
        modifier = Modifier.fillMaxWidth(),
        enabled = otpCode.isNotBlank(),
        label = stringResource(AppRes.string.send_otp_code)
    ) {
        onAction(ForgotPasswordAction.CheckOtp)
    }
}