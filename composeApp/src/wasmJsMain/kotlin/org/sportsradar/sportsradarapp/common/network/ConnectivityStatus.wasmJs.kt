package org.sportsradar.sportsradarapp.common.network

import kotlinx.browser.window
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.w3c.dom.events.Event

class WasmConnectivityStatus : ConnectivityStatus {
    private val _networkStateFlow = MutableStateFlow(
        if (window.navigator.onLine) {
            ConnectivityStatusState.CONNECTED
        } else {
            ConnectivityStatusState.DISCONNECTED
        }
    )
    override val networkStateFlow = _networkStateFlow.asStateFlow()

    override val networkState: ConnectivityStatusState
        get() = _networkStateFlow.value

    private val onlineListener: (Event) -> Unit = {
        _networkStateFlow.value = ConnectivityStatusState.CONNECTED
    }

    private val offlineListener: (Event) -> Unit = {
        _networkStateFlow.value = ConnectivityStatusState.DISCONNECTED
    }

    init {
        window.addEventListener("online", onlineListener)
        window.addEventListener("offline", offlineListener)
    }
}