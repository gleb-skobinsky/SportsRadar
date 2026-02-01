package org.sportsradar.sportsradarapp.common.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.StateFlow
import org.sportsradar.sportsradarapp.settings.presentation.SportsRadarLocale
import platform.Foundation.NSLocale
import platform.Foundation.NSUserDefaults
import platform.Foundation.preferredLanguages

private const val LANG_KEY = "AppleLanguages"

@Composable
internal actual fun ObserveLocaleUpdates(
    locale: StateFlow<SportsRadarLocale>
) {
    LaunchedEffect(Unit) {
        locale.collect { lang ->
            setIosLocale(lang)
        }
    }
}

internal fun setIosLocale(locale1: SportsRadarLocale) {
    NSUserDefaults.standardUserDefaults.setObject(
        value = listOf(locale1.isoCode),
        forKey = LANG_KEY
    )
}

internal actual fun getDefaultLocale(): SportsRadarLocale {
    val isoCode = NSLocale.preferredLanguages.first() as String
    return SportsRadarLocale.fromIsoCode(isoCode)
}