package org.sportsradar.sportsradarapp.init.presentation

import androidx.lifecycle.viewModelScope
import org.sportsradar.sportsradarapp.auth.domain.AuthRepository
import org.sportsradar.sportsradarapp.common.mvi.BaseAction
import org.sportsradar.sportsradarapp.common.mvi.BaseEffect
import org.sportsradar.sportsradarapp.common.mvi.BaseState
import org.sportsradar.sportsradarapp.common.mvi.BaseViewModel
import org.sportsradar.sportsradarapp.common.network.onError
import org.sportsradar.sportsradarapp.common.network.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class InitStateController :
    BaseViewModel<InitState, InitAction, InitEffect>(InitState()) {
    private val authRepository: AuthRepository by inject()

    override suspend fun performOnAction(action: InitAction) {
        when (action) {
            InitAction.Logout -> setEffect(InitEffect.ShowLogin)
        }
    }

    private var onCheckSession: (Boolean) -> Unit = {}

    init {
        viewModelScope.launch {
            authRepository.checkSession()
                .onSuccess {
                    setEffect(InitEffect.ShowContent)
                }
                .onError {
                    setEffect(InitEffect.ShowLogin)
                }
            // delay to take navigation animation into account.
            // maybe there is a better way to do this.
            delay(300)
            onCheckSession(false)
            setState { it.copy(sessionCheckLoading = false) }
        }
    }

    @Suppress("Unused") // used in swift
    fun setOnCheckSessionListener(onCheck: (Boolean) -> Unit) {
        onCheckSession = onCheck
    }
}

data class InitState(
    val sessionCheckLoading: Boolean = true
) : BaseState

sealed interface InitAction : BaseAction {
    data object Logout : InitAction
}

sealed interface InitEffect : BaseEffect {
    data object ShowLogin : InitEffect
    data object ShowContent : InitEffect
}