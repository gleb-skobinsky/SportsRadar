package org.sportsradar.sportsradarapp.common.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
            window.__customLocale = lang.isoCode.replace('_', '-')
        }
    }
}