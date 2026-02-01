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
            val jvmLocale = Locale.forLanguageTag(lang.isoCode)
            Locale.setDefault(jvmLocale)
        }
    }
}