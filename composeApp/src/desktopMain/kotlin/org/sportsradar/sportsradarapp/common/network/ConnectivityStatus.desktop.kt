package org.sportsradar.sportsradarapp.common.network

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.net.InetAddress
import kotlin.time.Duration.Companion.seconds

class DesktopConnectivityStatus: ConnectivityStatus {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _networkStateFlow = MutableStateFlow(ConnectivityStatusState.UNKNOWN)
    override val networkStateFlow: StateFlow<ConnectivityStatusState> = _networkStateFlow.asStateFlow()

    override val networkState: ConnectivityStatusState
        get() = _networkStateFlow.value

    private var isMonitoring = false

    init {
        isMonitoring = true
        coroutineScope.launch {
            while (isMonitoring && isActive) {
                val reachable = isInternetReachable()
                _networkStateFlow.value = if (reachable) {
                    ConnectivityStatusState.CONNECTED
                } else {
                    ConnectivityStatusState.DISCONNECTED
                }

                delay(5.seconds)
            }
        }
    }

    private fun isInternetReachable(): Boolean {
        return try {
            val address = InetAddress.getByName("8.8.8.8")
            address.isReachable(1000) // ping Google DNS
        } catch (_: Exception) {
            false
        }
    }

    override fun cleanup() {
        isMonitoring = false
        coroutineScope.cancel()
    }
}
