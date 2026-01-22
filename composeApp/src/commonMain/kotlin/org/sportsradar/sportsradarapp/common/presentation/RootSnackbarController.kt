package org.sportsradar.sportsradarapp.common.presentation

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

object RootSnackbarController {
    val snackbarState = SnackbarHostState()
    private val scope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun showSnackbar(message: String) {
        scope.launch {
            snackbarState.showSnackbar(message)
        }
    }
}