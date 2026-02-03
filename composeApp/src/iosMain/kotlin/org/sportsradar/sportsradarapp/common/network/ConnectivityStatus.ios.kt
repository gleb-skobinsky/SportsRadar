package org.sportsradar.sportsradarapp.common.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_cancel
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_get_main_queue

class IosConnectivityStatus : ConnectivityStatus {
    private val _networkStateFlow = MutableStateFlow(
        ConnectivityStatusState.UNKNOWN
    )
    override val networkStateFlow = _networkStateFlow.asStateFlow()

    override val networkState: ConnectivityStatusState
        get() = _networkStateFlow.value

    private val monitor = nw_path_monitor_create()
    private var isMonitoring = false

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
        nw_path_monitor_set_update_handler(monitor) { path ->
            val status = nw_path_get_status(path)
            val newState = if (status == nw_path_status_satisfied) {
                ConnectivityStatusState.CONNECTED
            } else {
                ConnectivityStatusState.DISCONNECTED
            }

            // Use tryEmit for more efficient state updates
            updateNetworkState(newState)
        }

        nw_path_monitor_start(monitor)
        isMonitoring = true
    }

    private fun updateNetworkState(state: ConnectivityStatusState) {
        coroutineScope.launch {
            _networkStateFlow.value = state
        }
    }

    /**
     * Cleanup resources when the connectivity monitor is no longer needed.
     * This prevents memory leaks and ensures proper resource management.
     */
    override fun cleanup() {
        if (isMonitoring) {
            nw_path_monitor_cancel(monitor)
            isMonitoring = false
        }
    }
}
