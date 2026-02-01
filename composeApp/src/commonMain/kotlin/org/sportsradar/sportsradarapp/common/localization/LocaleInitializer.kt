package org.sportsradar.sportsradarapp.common.localization

import org.koin.core.KoinApplication
import org.koin.core.scope.Scope

fun interface LocaleInitializer {
    fun initialize()
}

internal expect fun Scope.getLocaleInitializer(): LocaleInitializer

internal fun KoinApplication.initializeLocale() {
    koin.get<LocaleInitializer>().initialize()
}