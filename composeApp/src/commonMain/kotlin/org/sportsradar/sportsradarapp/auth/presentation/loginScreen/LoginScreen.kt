package org.sportsradar.sportsradarapp.auth.presentation.loginScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.sportsradar.sportsradarapp.profile.presentation.ProfileTopBar
import org.sportsradar.sportsradarapp.common.icons.Lock
import org.sportsradar.sportsradarapp.common.icons.Sms
import org.sportsradar.sportsradarapp.common.mvi.CollectEffects
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator.Companion.PreviewNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.presentation.RootSnackbarController
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppButton
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppPasswordTextField
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppTextField
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer
import org.sportsradar.sportsradarapp.common.presentation.modifiers.noRippleClickable
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.email
import org.sportsradar.sportsradarapp.resources.forgot_password
import org.sportsradar.sportsradarapp.resources.log_in
import org.sportsradar.sportsradarapp.resources.logo
import org.sportsradar.sportsradarapp.resources.password
import org.sportsradar.sportsradarapp.resources.sign_up
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme
import org.sportsradar.uiKit.theme.SportsRadarTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel()
) {
    val navigator = LocalKmpNavigator.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.CollectEffects {
        when (it) {
            LoginEffect.NavigateToMain -> navigator.replaceAll(Screens.ProfileScreen)
            is LoginEffect.ShowError -> RootSnackbarController.showSnackbar(it.message)
        }
    }
    LoginScreenContent(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@Composable
fun LoginScreenContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    navigator: KMPNavigator
) {
    SportsRadarScaffold(
        topBar = {
            ProfileTopBar()
        }
    ) {
        22.dp.VerticalSpacer()
        SportsRadarAppLogo()
        50.dp.VerticalSpacer()
        AuthTopTwoButtons(AppRes.string.log_in, AppRes.string.sign_up) {
            navigator.goTo(Screens.SignupScreen)
        }
        40.dp.VerticalSpacer()
        SportsRadarAppTextField(
            value = state.email,
            onValueChange = {
                onAction(LoginAction.SetEmail(it))
            },
            leftIcon = Sms,
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(AppRes.string.email)
        )
        24.dp.VerticalSpacer()
        SportsRadarAppPasswordTextField(
            value = state.password,
            onValueChange = {
                onAction(LoginAction.SetPassword(it))
            },
            modifier = Modifier.fillMaxWidth(),
            placeholder = stringResource(AppRes.string.password),
            leftIcon = Lock
        )
        24.dp.VerticalSpacer()
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .noRippleClickable {
                    navigator.goTo(Screens.ForgotPasswordScreen())
                },
            text = stringResource(AppRes.string.forgot_password),
            style = LocalSportsRadarTheme.typography.bodyMedium,
            color = LocalSportsRadarTheme.colors.primaryContainer
        )
        40.dp.VerticalSpacer()
        SportsRadarAppButton(
            isLoading = state.isLoading,
            label = stringResource(AppRes.string.log_in),
            modifier = Modifier.fillMaxWidth(),
            enabled = state.canLogin
        ) {
            onAction(LoginAction.DoLogIn)
        }
        24.dp.VerticalSpacer()
    }
}

@Composable
internal fun ColumnScope.SportsRadarAppLogo() {
    Image(
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .size(240.dp),
        imageVector = vectorResource(AppRes.drawable.logo),
        contentDescription = "Payeasy logo"
    )
}

@Composable
internal fun AuthTopTwoButtons(
    firstText: StringResource,
    secondText: StringResource,
    onClickSecond: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        BigAuthText(stringResource(firstText))
        BigAuthText(stringResource(secondText), true, onClickSecond)
    }
}

@Composable
fun BigAuthText(
    text: String,
    secondary: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    Text(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .clickable(enabled = onClick != null) {
                onClick?.invoke()
            },
        text = text,
        color = if (secondary)
            LocalSportsRadarTheme.colors.secondaryContainer
        else
            LocalSportsRadarTheme.colors.primaryContainer,
        style = LocalSportsRadarTheme.typography.headlineLarge.copy(
            fontSize = if (secondary) 24.sp else 32.sp
        )
    )
}

@Composable
@Preview
fun LoginScreenPreview() {
    SportsRadarTheme {
        LoginScreenContent(
            state = LoginState(),
            onAction = {},
            navigator = PreviewNavigator
        )
    }
}