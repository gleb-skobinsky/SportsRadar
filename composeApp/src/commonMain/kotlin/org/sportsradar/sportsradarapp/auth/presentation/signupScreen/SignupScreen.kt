package org.sportsradar.sportsradarapp.auth.presentation.signupScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.AuthTopTwoButtons
import org.sportsradar.sportsradarapp.auth.presentation.loginScreen.SportsRadarAppLogo
import org.sportsradar.sportsradarapp.common.icons.Lock
import org.sportsradar.sportsradarapp.common.icons.LockHidden
import org.sportsradar.sportsradarapp.common.icons.Sms
import org.sportsradar.sportsradarapp.common.mvi.CollectEffects
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.presentation.RootSnackbarController
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppButton
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppPasswordTextField
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppTextField
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.enter_email
import org.sportsradar.sportsradarapp.resources.log_in
import org.sportsradar.sportsradarapp.resources.make_up_password
import org.sportsradar.sportsradarapp.resources.only_latin_symbols
import org.sportsradar.sportsradarapp.resources.password_contains_special_symbols
import org.sportsradar.sportsradarapp.resources.password_is_long_enough
import org.sportsradar.sportsradarapp.resources.passwords_match
import org.sportsradar.sportsradarapp.resources.repeat_password
import org.sportsradar.sportsradarapp.resources.sign_up
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
fun SignupScreen(
    viewModel: SignupViewModel = koinViewModel()
) {
    val navigator = LocalKmpNavigator.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.CollectEffects {
        when (it) {
            is SignupEffect.ShowError -> RootSnackbarController.showSnackbar(it.message)
            SignupEffect.Success -> navigator.replaceAll(Screens.HomeScreen)
        }
    }
    SignupScreenContent(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@Composable
fun SignupScreenContent(
    state: SignupState,
    onAction: (SignupAction) -> Unit,
    navigator: KMPNavigator
) {
    SportsRadarScaffold {
        22.dp.VerticalSpacer()
        SportsRadarAppLogo()
        50.dp.VerticalSpacer()
        AuthTopTwoButtons(AppRes.string.sign_up, AppRes.string.log_in) {
            navigator.goTo(Screens.LoginScreen)
        }
        40.dp.VerticalSpacer()
        SportsRadarAppTextField(
            value = state.email,
            onValueChange = {
                onAction(SignupAction.UpdateEmail(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(AppRes.string.enter_email),
            leftIcon = Sms,
        )
        24.dp.VerticalSpacer()
        SportsRadarAppPasswordTextField(
            value = state.password,
            onValueChange = {
                onAction(SignupAction.UpdatePassword(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(AppRes.string.make_up_password),
            leftIcon = Lock
        )
        24.dp.VerticalSpacer()
        SportsRadarAppPasswordTextField(
            value = state.repeatPassword,
            onValueChange = {
                onAction(SignupAction.UpdateRepeatPassword(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(AppRes.string.repeat_password),
            leftIcon = LockHidden
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
            label = stringResource(AppRes.string.sign_up),
            modifier = Modifier.fillMaxWidth(),
            enabled = state.canRegister
        ) {
            onAction(SignupAction.DoSignUp)
        }
        24.dp.VerticalSpacer()
    }
}

@Composable
internal fun PasswordConditions(
    passwordsMatch: Boolean,
    containOnlyLatin: Boolean,
    isLongEnough: Boolean,
    hasSpecialSymbols: Boolean
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PasswordCondition(AppRes.string.passwords_match, passwordsMatch)
        PasswordCondition(AppRes.string.only_latin_symbols, containOnlyLatin)
        PasswordCondition(AppRes.string.password_is_long_enough, isLongEnough)
        PasswordCondition(
            AppRes.string.password_contains_special_symbols,
            hasSpecialSymbols
        )
    }
}

@Composable
internal fun PasswordCondition(
    label: StringResource,
    isSatisfied: Boolean
) {
    Row {
        Text(
            text = "• ",
            style = LocalSportsRadarTheme.typography.bodySmall,
            color = if (isSatisfied)
                LocalSportsRadarTheme.colors.primary
            else
                LocalSportsRadarTheme.colors.secondaryContainer
        )
        Text(
            text = stringResource(label),
            style = LocalSportsRadarTheme.typography.bodySmall,
            color = if (isSatisfied)
                LocalSportsRadarTheme.colors.primaryContainer
            else
                LocalSportsRadarTheme.colors.secondaryContainer
        )
    }
}