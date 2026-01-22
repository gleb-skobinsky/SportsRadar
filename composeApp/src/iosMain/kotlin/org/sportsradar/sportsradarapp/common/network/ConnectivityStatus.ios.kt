package org.sportsradar.sportsradarapp.common.network

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import platform.Network.nw_path_get_status
import platform.Network.nw_path_monitor_create
import platform.Network.nw_path_monitor_set_queue
import platform.Network.nw_path_monitor_set_update_handler
import platform.Network.nw_path_monitor_start
import platform.Network.nw_path_status_satisfied
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue

class IosConnectivityStatus : ConnectivityStatus {
    private val _networkStateFlow =
        MutableStateFlow(ConnectivityStatusState.UNKNOWN)
    override val networkStateFlow = _networkStateFlow.asStateFlow()

    override val networkState: ConnectivityStatusState
        get() = _networkStateFlow.value

    private val monitor = nw_path_monitor_create()

    init {
        nw_path_monitor_set_queue(monitor, dispatch_get_main_queue())
        nw_path_monitor_set_update_handler(monitor) { path ->
            val status = nw_path_get_status(path)
            if (status == nw_path_status_satisfied) {
                dispatch_async(dispatch_get_main_queue()) {
                    _networkStateFlow.value = ConnectivityStatusState.CONNECTED
                }
            } else {
                dispatch_async(dispatch_get_main_queue()) {
                    _networkStateFlow.value =
                        ConnectivityStatusState.DISCONNECTED
                }
            }
        }

        nw_path_monitor_start(monitor)
    }
}
