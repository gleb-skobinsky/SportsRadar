package org.sportsradar.sportsradarapp.common.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

interface BaseState

interface BaseAction

interface BaseEffect

abstract class BaseViewModel<S : BaseState, A : BaseAction, E : BaseEffect>(
    initState: S
) : ViewModel(), KoinComponent {
    private val _state = MutableStateFlow(initState)
    val state: StateFlow<S> = _state.asStateFlow()

    protected val currentState: S
        get() = state.value

    private var actionJob: Job? = null

    private var prevAction: A? = null
    fun onAction(action: A) {
        if (
            prevAction?.equals(action) == true &&
            actionJob?.isActive == true
        ) {
            return
        }
        prevAction = action
        actionJob = viewModelScope.launch {
            performOnAction(action)
        }
    }

    protected abstract suspend fun performOnAction(action: A)

    private val _effect: MutableSharedFlow<E> = MutableSharedFlow()
    val effect: Flow<E> = _effect.asSharedFlow()

    protected fun setEffect(effect: E) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    protected fun setState(transform: (S) -> S) {
        _state.update(transform)
    }
}