package org.sportsradar.sportsradarapp.common.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.StateFlow
import org.sportsradar.sportsradarapp.settings.presentation.SportsRadarLocale
import java.util.Locale

@Composable
internal actual fun ObserveLocaleUpdates(locale: StateFlow<SportsRadarLocale>) {
    LaunchedEffect(Unit) {
        locale.collect { lang ->
            setDesktopLocale(lang)
        }
    }
}

internal fun setDesktopLocale(lang: SportsRadarLocale) {
    val jvmLocale = Locale.forLanguageTag(lang.isoCode)
    Locale.setDefault(jvmLocale)
}

internal actual fun getDefaultLocale(): SportsRadarLocale {
    return SportsRadarLocale.fromIsoCode(Locale.getDefault().language)
}