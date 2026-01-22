package org.sportsradar.sportsradarapp.di

import org.koin.core.scope.Scope
import org.sportsradar.sportsradarapp.common.network.AndroidConnectivityStatus
import org.sportsradar.sportsradarapp.common.network.ConnectivityStatus

actual fun Scope.getConnectivityStatus(): ConnectivityStatus {
    return AndroidConnectivityStatus(context = get())
}