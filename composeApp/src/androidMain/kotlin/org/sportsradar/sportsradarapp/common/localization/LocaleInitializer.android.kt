package org.sportsradar.sportsradarapp.common.localization

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import org.koin.core.scope.Scope
import org.sportsradar.sportsradarapp.settings.presentation.LocaleStorage

internal actual fun Scope.getLocaleInitializer() = LocaleInitializer {
    val localeStorage: LocaleStorage = get()
    val localeListCompat = LocaleListCompat.forLanguageTags(
        localeStorage.getLocale().isoCode
    )
    AppCompatDelegate.setApplicationLocales(localeListCompat)
}
