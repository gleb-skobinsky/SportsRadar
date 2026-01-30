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
import org.sportsradar.sportsradarapp.common.network.onSuccess
import org.sportsradar.sportsradarapp.profile.data.ProfileRepository
import org.sportsradar.sportsradarapp.profile.presentation.model.UiUserData
import org.sportsradar.sportsradarapp.profile.presentation.model.toUserUpdate
import kotlin.jvm.JvmInline

@Stable
internal class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val profileRepository: ProfileRepository,
) :
    BaseViewModel<ProfileState, ProfileAction, BaseEffect>(ProfileState.Loading) {

    init {
        checkSession()
        subscribeToUserData()
    }

    private fun subscribeToUserData() {
        viewModelScope.launch {
            authRepository.subscribeToUserData().collectLatest { user ->
                setState { oldState ->
                    if (user != null) {
                        val user = UiUserData(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            email = user.email,
                        )
                        if (oldState is ProfileState.Authenticated) {
                            oldState.copy(
                                userData = user,
                                tempData = user,
                            )
                        } else {
                            ProfileState.Authenticated(
                                userData = user,
                                tempData = user,
                                isBeingEdited = false,
                                userSaveLoading = false,
                            )
                        }
                    } else {
                        ProfileState.Anonymous
                    }
                }
            }
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            profileRepository.checkSession()
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
                        isBeingEdited = !oldState.isBeingEdited,
                        tempData = oldState.userData
                    )
                }
            }

            is ProfileAction.UpdateLastName -> {
                setStateAuthenticated { oldState ->
                    oldState.copy(
                        tempData = oldState.tempData.copy(lastName = action.value)
                    )
                }
            }

            is ProfileAction.UpdateFirstName -> {
                setStateAuthenticated { oldState ->
                    oldState.copy(
                        tempData = oldState.tempData.copy(firstName = action.value)
                    )
                }
            }

            ProfileAction.SaveChanges -> {
                saveProfile()
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

    private suspend fun saveProfile() {
        val currentState = (state.value as? ProfileState.Authenticated) ?: return
        val tempData = currentState.tempData
        setStateAuthenticated {
            it.copy(userSaveLoading = true)
        }
        profileRepository.updateUserData(user = tempData.toUserUpdate())
            .onSuccess {
                setStateAuthenticated {
                    it.copy(userData = tempData)
                }
            }
        setStateAuthenticated {
            it.copy(userSaveLoading = false)
        }
    }
}

internal sealed interface ProfileState : BaseState {
    val isEditable: Boolean get() = false

    object Loading : ProfileState

    object Anonymous : ProfileState

    data class Authenticated(
        val userData: UiUserData,
        val tempData: UiUserData,
        val isBeingEdited: Boolean,
        val userSaveLoading: Boolean,
    ) : ProfileState {
        override val isEditable: Boolean = true
        val canSave: Boolean = userData != tempData
    }
}

internal sealed interface ProfileAction : BaseAction {
    object Logout : ProfileAction
    object SwitchToEditMode : ProfileAction

    @JvmInline
    value class UpdateFirstName(
        val value: String,
    ) : ProfileAction

    @JvmInline
    value class UpdateLastName(
        val value: String,
    ) : ProfileAction

    object SaveChanges : ProfileAction
}