package org.sportsradar.sportsradarapp.common.network

import kotlinx.coroutines.flow.StateFlow

interface ConnectivityStatus {
    val networkStateFlow: StateFlow<ConnectivityStatusState>
    val networkState: ConnectivityStatusState
}

enum class ConnectivityStatusState(val connected: Boolean) {
    UNKNOWN(false),
    DISCONNECTED(false),
    CONNECTED(true)
}