package org.sportsradar.sportsradarapp.profile.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.sportsradar.sportsradarapp.common.navigation.KMPNavigator
import org.sportsradar.sportsradarapp.common.navigation.LocalKmpNavigator
import org.sportsradar.sportsradarapp.common.navigation.Screens
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppButton
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarAppTextField
import org.sportsradar.sportsradarapp.common.presentation.components.SportsRadarScaffold
import org.sportsradar.sportsradarapp.common.presentation.components.VerticalSpacer
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.email
import org.sportsradar.sportsradarapp.resources.log_in
import org.sportsradar.sportsradarapp.resources.log_out
import org.sportsradar.sportsradarapp.resources.profile_screen_header
import org.sportsradar.sportsradarapp.resources.save_profile
import org.sportsradar.sportsradarapp.resources.user_first_name
import org.sportsradar.sportsradarapp.resources.user_last_name
import org.sportsradar.uiKit.components.FlippableCard
import org.sportsradar.uiKit.components.SportsRadarTopBar
import org.sportsradar.uiKit.icons.IcEdit
import org.sportsradar.uiKit.theme.LocalSportsRadarTheme

@Composable
internal fun ProfileScreen() {
    val viewModel: ProfileViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val navigator = LocalKmpNavigator.current
    ProfileScreenContent(
        state = state,
        onAction = viewModel::onAction,
        navigator = navigator
    )
}

@Composable
private fun ProfileScreenContent(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    navigator: KMPNavigator,
) {
    SportsRadarScaffold(
        alignment = Alignment.CenterHorizontally,
        horizontalPadding = 16.dp,
        topBar = {
            ProfileTopBar(
                onClickEdit = if (state.isEditable) {
                    { onAction(ProfileAction.SwitchToEditMode) }
                } else {
                    null
                }
            )
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
                AuthenticatedProfileContent(state, onAction)
            }
        }
    }
}

@Composable
private fun AuthenticatedProfileContent(
    state: ProfileState.Authenticated,
    onAction: (ProfileAction) -> Unit
) {
    FlippableCard(
        isFlipped = state.isBeingEdited,
        front = {
            CommonProfileColumn {
                40.dp.VerticalSpacer()
                LabelWithDescription(
                    label = state.userData.firstName,
                    description = stringResource(AppRes.string.user_first_name)
                )
                20.dp.VerticalSpacer()
                LabelWithDescription(
                    label = state.userData.lastName,
                    description = stringResource(AppRes.string.user_last_name)
                )
                20.dp.VerticalSpacer()
                LabelWithDescription(
                    label = state.userData.email,
                    description = stringResource(AppRes.string.email)
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
        },
        back = {
            CommonProfileColumn {
                40.dp.VerticalSpacer()
                SportsRadarAppTextField(
                    placeholder = stringResource(AppRes.string.user_first_name),
                    backgroundColor = LocalSportsRadarTheme.colors.surface,
                    value = state.tempData.firstName,
                    onValueChange = {
                        onAction(ProfileAction.UpdateFirstName(it))
                    }
                )
                20.dp.VerticalSpacer()
                SportsRadarAppTextField(
                    placeholder = stringResource(AppRes.string.user_last_name),
                    backgroundColor = LocalSportsRadarTheme.colors.surface,
                    value = state.tempData.lastName,
                    onValueChange = {
                        onAction(ProfileAction.UpdateLastName(it))
                    }
                )
                32.dp.VerticalSpacer()
                SportsRadarAppButton(
                    label = stringResource(AppRes.string.save_profile),
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.canSave,
                    rightIcon = null,
                    isLoading = state.userSaveLoading,
                ) {
                    onAction(ProfileAction.SaveChanges)
                }
                24.dp.VerticalSpacer()
            }
        }
    )
}

@Composable
private inline fun CommonProfileColumn(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(LocalSportsRadarTheme.colors.tertiary)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}

@Composable
private fun LabelWithDescription(
    label: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = description,
            textAlign = TextAlign.Center,
            style = LocalSportsRadarTheme.typography.bodyMedium,
            color = LocalSportsRadarTheme.colors.secondary
        )
        Text(
            text = label,
            textAlign = TextAlign.Center,
            style = LocalSportsRadarTheme.typography.bodyLarge,
            color = LocalSportsRadarTheme.colors.secondary
        )
    }
}

@Composable
internal fun ProfileTopBar(
    onClickEdit: (() -> Unit)? = null,
) {
    val iconEdit = onClickEdit?.let { IcEdit }
    val navigator = LocalKmpNavigator.current
    SportsRadarTopBar(
        title = stringResource(AppRes.string.profile_screen_header),
        onBackClick = navigator::goBack,
        action = iconEdit,
        onActionClick = onClickEdit
    )
}