package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sportsradar.sportsradarapp.common.localization.LocaleProvider

@Stable
class LocaleViewModel : LocaleProvider, ViewModel() {
    private val _locale = MutableStateFlow(SportsRadarLocale.English)
    override val locale = _locale.asStateFlow()

    override fun updateLocale(locale: SportsRadarLocale) {
        _locale.value = locale
    }
}