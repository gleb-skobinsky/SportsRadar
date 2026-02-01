package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.runtime.Immutable

@Immutable
enum class SportsRadarLocale(val isoCode: String) {
    English("en"),
    Russian("ru")
}