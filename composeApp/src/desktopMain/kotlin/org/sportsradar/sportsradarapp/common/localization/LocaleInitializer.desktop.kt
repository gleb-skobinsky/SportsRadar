package org.sportsradar.sportsradarapp.common.localization

import org.koin.core.scope.Scope
import org.sportsradar.sportsradarapp.settings.presentation.LocaleStorage

internal actual fun Scope.getLocaleInitializer(): LocaleInitializer {
    return LocaleInitializer {
        val localeStorage: LocaleStorage = get()
        setDesktopLocale(localeStorage.getLocale())
    }
}