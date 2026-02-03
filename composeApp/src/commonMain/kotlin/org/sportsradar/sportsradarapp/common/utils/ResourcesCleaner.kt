package org.sportsradar.sportsradarapp.common.utils

import org.sportsradar.sportsradarapp.common.network.ConnectivityStatus

class ResourcesCleaner(
    private val connectivityStatus: ConnectivityStatus,
) {
    fun cleanup() {
        connectivityStatus.cleanup()
    }
}