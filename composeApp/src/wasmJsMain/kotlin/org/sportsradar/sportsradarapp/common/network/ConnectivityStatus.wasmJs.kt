package org.sportsradar.sportsradarapp.common.network

import kotlinx.browser.window
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.w3c.dom.events.Event

private const val ONLINE_EVENT_KEY = "online"
private const val OFFLINE_EVENT_KEY = "offline"

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

    private var isMonitoring = false

    init {
        isMonitoring = true
        window.addEventListener(ONLINE_EVENT_KEY, onlineListener)
        window.addEventListener(OFFLINE_EVENT_KEY, offlineListener)
    }

    override fun cleanup() {
        if (isMonitoring) {
            window.removeEventListener(ONLINE_EVENT_KEY, onlineListener)
            window.removeEventListener(OFFLINE_EVENT_KEY, offlineListener)
            isMonitoring = false
        }
    }
}