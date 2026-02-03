package org.sportsradar.sportsradarapp.common.network

import kotlinx.coroutines.flow.StateFlow

interface ConnectivityStatus {
    val networkStateFlow: StateFlow<ConnectivityStatusState>
    val networkState: ConnectivityStatusState
    
    /**
     * Cleanup resources and stop monitoring connectivity status.
     * This should be called when the connectivity monitor is no longer needed
     * to prevent memory leaks and resource waste.
     */
    fun cleanup()
}

enum class ConnectivityStatusState(val connected: Boolean) {
    UNKNOWN(false),
    DISCONNECTED(false),
    CONNECTED(true)
}