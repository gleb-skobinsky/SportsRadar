package org.sportsradar.sportsradarapp.common.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.StateFlow
import org.sportsradar.sportsradarapp.settings.presentation.SportsRadarLocale
import platform.Foundation.NSUserDefaults

private const val LANG_KEY = "AppleLanguages"

@Composable
internal actual fun ObserveLocaleUpdates(
    locale: StateFlow<SportsRadarLocale>
) {
    LaunchedEffect(Unit) {
        locale.collect {
            NSUserDefaults.standardUserDefaults.setObject(
                value = listOf(it.isoCode),
                forKey = LANG_KEY
            )
        }
    }
}