package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Immutable
@Serializable
enum class SportsRadarLocale(val isoCode: String) {
    @SerialName("en")
    English("en"),
    @SerialName("ru")
    Russian("ru");

    companion object {
        fun fromIsoCode(localeCode: String): SportsRadarLocale {
            val isoCode = localeCode
                .substringBefore('-')
                .substringBefore('_')
                .lowercase()
            return entries.find { it.isoCode == isoCode } ?: English
        }
    }
}