package org.sportsradar.sportsradarapp.profile.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.viewModelScope
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
        checkSession()
        subscribeToUserData()
    }

    private fun subscribeToUserData() {
        viewModelScope.launch {
            authRepository.subscribeToUserData().collectLatest { user ->
                setState {
                    if (user != null) {
                        ProfileState.Authenticated(
                            userFirstName = user.firstName,
                            userLastName = user.lastName,
                            email = user.email,
                            isEdited = false
                        )
                    } else {
                        ProfileState.Anonymous
                    }
                }
            }
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            authRepository.checkSession()
        }
    }

    override suspend fun performOnAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.Logout -> {
                performLogout()
            }

            ProfileAction.SwitchToEditMode -> {
                setStateAuthenticated { oldState ->
                    oldState.copy(
                        isEdited = !oldState.isEdited
                    )
                }
            }
        }
    }

    private inline fun setStateAuthenticated(
        crossinline block: (ProfileState.Authenticated) -> ProfileState.Authenticated
    ) {
        setState { oldState ->
            if (oldState is ProfileState.Authenticated) {
                block(oldState)
            } else {
                oldState
            }
        }
    }


    private suspend fun performLogout() {
        setState { ProfileState.Loading }
        authRepository.logout()
    }
}

internal sealed interface ProfileState : BaseState {
    val isEditable: Boolean get() = false

    object Loading : ProfileState

    object Anonymous : ProfileState

    data class Authenticated(
        val userFirstName: String,
        val userLastName: String,
        val email: String,
        val isEdited: Boolean,
    ) : ProfileState {
        override val isEditable: Boolean = true
    }
}

internal sealed interface ProfileAction : BaseAction {
    object Logout : ProfileAction
    object SwitchToEditMode : ProfileAction
}