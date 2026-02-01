package org.sportsradar.sportsradarapp.common.localization

import kotlinx.coroutines.flow.StateFlow
import org.sportsradar.sportsradarapp.settings.presentation.SportsRadarLocale

interface LocaleProvider {
    val locale: StateFlow<SportsRadarLocale>

    fun updateLocale(locale: SportsRadarLocale)
}