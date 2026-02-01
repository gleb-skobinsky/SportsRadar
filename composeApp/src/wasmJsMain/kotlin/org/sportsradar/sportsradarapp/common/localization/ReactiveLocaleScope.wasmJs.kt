package org.sportsradar.sportsradarapp.common.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.intl.Locale
import kotlinx.coroutines.flow.StateFlow
import org.sportsradar.sportsradarapp.settings.presentation.SportsRadarLocale

@Suppress("ClassName", "ObjectPropertyName")
external object window {
    var __customLocale: String?
}

@Composable
internal actual fun ObserveLocaleUpdates(
    locale: StateFlow<SportsRadarLocale>,
) {
    LaunchedEffect(locale) {
        locale.collect { lang ->
            setWasmLocale(lang)
        }
    }
}

internal fun setWasmLocale(locale: SportsRadarLocale) {
    window.__customLocale = locale.isoCode.replace('_', '-')
}

internal actual fun getDefaultLocale(): SportsRadarLocale {
    return SportsRadarLocale.fromIsoCode(Locale.current.language)
}