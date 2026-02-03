package org.sportsradar.sportsradarapp.common.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidConnectivityStatus(
    private val context: Context
) : ConnectivityStatus {
    private val _networkStateFlow = MutableStateFlow(ConnectivityStatusState.UNKNOWN)
    override val networkStateFlow = _networkStateFlow.asStateFlow()
    override val networkState: ConnectivityStatusState
        get() = _networkStateFlow.value

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _networkStateFlow.value = ConnectivityStatusState.CONNECTED
        }

        override fun onLost(network: Network) {
            _networkStateFlow.value = ConnectivityStatusState.DISCONNECTED
        }
    }

    private var connectivityManager: ConnectivityManager? = null

    init {
        try {
            connectivityManager =
                context.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
                .build()
            connectivityManager?.requestNetwork(request, networkCallback)

            // For the first app start
            if (isNetworkAvailable(connectivityManager)) {
                _networkStateFlow.value = ConnectivityStatusState.CONNECTED
            } else {
                _networkStateFlow.value = ConnectivityStatusState.DISCONNECTED
            }
        } catch (_: Exception) {
            _networkStateFlow.value = ConnectivityStatusState.UNKNOWN
        }
    }

    private fun isNetworkAvailable(
        connectivityManager: ConnectivityManager?,
    ): Boolean {
        val network = connectivityManager?.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

        return with(activeNetwork) {
            hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                    hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED) && (
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    )
        }
    }

    override fun cleanup() {
        connectivityManager?.unregisterNetworkCallback(networkCallback)
        connectivityManager = null
    }
}
