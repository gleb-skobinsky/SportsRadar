package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource
import org.sportsradar.sportsradarapp.resources.AppRes
import org.sportsradar.sportsradarapp.resources.locale_de
import org.sportsradar.sportsradarapp.resources.locale_en
import org.sportsradar.sportsradarapp.resources.locale_ru

@Immutable
@Serializable
enum class SportsRadarLocale(
    val isoCode: String,
    val label: StringResource,
) {
    @SerialName("en")
    English(
        isoCode = "en",
        label = AppRes.string.locale_en
    ),
    @SerialName("ru")
    Russian(
        isoCode = "ru",
        label = AppRes.string.locale_ru
    ),
    German(
        isoCode = "de",
        label = AppRes.string.locale_de
    );

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