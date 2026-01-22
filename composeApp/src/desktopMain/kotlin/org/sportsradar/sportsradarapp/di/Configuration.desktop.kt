package org.sportsradar.sportsradarapp.di

import org.koin.core.scope.Scope
import org.sportsradar.sportsradarapp.common.network.ConnectivityStatus
import org.sportsradar.sportsradarapp.common.network.DesktopConnectivityStatus

actual fun Scope.getConnectivityStatus(): ConnectivityStatus =
    DesktopConnectivityStatus()