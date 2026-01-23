package org.sportsradar.sportsradarapp.auth.presentation.profileScreen

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.sportsradar.sportsradarapp.auth.domain.AuthRepository
import org.sportsradar.sportsradarapp.common.mvi.BaseAction
import org.sportsradar.sportsradarapp.common.mvi.BaseEffect
import org.sportsradar.sportsradarapp.common.mvi.BaseState
import org.sportsradar.sportsradarapp.common.mvi.BaseViewModel

@Stable
internal class ProfileViewModel(
    private val authRepository: AuthRepository,
) :
    BaseViewModel<ProfileState, ProfileAction, BaseEffect>(ProfileState.Loading) {

    init {
        viewModelScope.launch {
            authRepository.subscribeToUserData().collectLatest { user ->
                setState {
                    if (user != null) {
                        ProfileState.Authenticated(
                            userFirstName = "",
                            userLastName = "",
                            email = user.email
                        )
                    } else {
                        ProfileState.Anonymous
                    }
                }
            }
        }
    }

    override suspend fun performOnAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.Logout -> {
                performLogout()
            }
        }
    }


    private suspend fun performLogout() {
        setState { ProfileState.Loading }
        authRepository.logout()
    }
}

internal sealed interface ProfileState : BaseState {
    object Loading : ProfileState

    object Anonymous : ProfileState

    data class Authenticated(
        val userFirstName: String,
        val userLastName: String,
        val email: String,
    ) : ProfileState
}

internal sealed interface ProfileAction : BaseAction {
    object Logout : ProfileAction
}
