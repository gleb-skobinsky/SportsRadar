package org.sportsradar.sportsradarapp.auth.presentation.profileScreen

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppButton
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.log_in
import org.sportsradar.sportsradarapp.resources.log_out
import org.sportsradar.sportsradarapp.resources.profile_screen_header
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
internal fun ProfileScreen() {
    val viewModel: ProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigator = LocalKmpNavigator.current
    ProfileScreenContent(state, viewModel::onAction, navigator)
}

@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    navigator: KMPNavigator,
) {
    SportsRadarScaffold(
        alignment = Alignment.CenterHorizontally,
        topBar = {
            ProfileTopBar()
        }
    ) {
        when (state) {
            ProfileState.Loading -> Unit
            ProfileState.Anonymous -> {
                40.dp.VerticalSpacer()
                SportsRadarAppButton(
                    label = stringResource(AppRes.string.log_in),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    navigator.goTo(Screens.LoginScreen)
                }
                24.dp.VerticalSpacer()
            }

            is ProfileState.Authenticated -> {
                40.dp.VerticalSpacer()
                Text(
                    text = state.email,
                    textAlign = TextAlign.Center,
                    style = LocalSportsRadarTheme.typography.bodyLarge,
                    color = LocalSportsRadarTheme.colors.secondary
                )
                32.dp.VerticalSpacer()
                SportsRadarAppButton(
                    label = stringResource(AppRes.string.log_out),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onAction(ProfileAction.Logout)
                }
                24.dp.VerticalSpacer()
            }
        }
    }
}

@Composable
internal fun BoxScope.ProfileTopBar() {
    Text(
        text = stringResource(AppRes.string.profile_screen_header),
        style = LocalSportsRadarTheme.typography.headlineLarge,
        color = LocalSportsRadarTheme.colors.secondary,
        modifier = Modifier.align(Alignment.Center),
    )
}