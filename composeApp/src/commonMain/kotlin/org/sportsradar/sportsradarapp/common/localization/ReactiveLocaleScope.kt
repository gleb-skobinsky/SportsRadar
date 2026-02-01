package org.sportsradar.sportsradarapp.common.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.StateFlow
import org.sportsradar.sportsradarapp.settings.presentation.SportsRadarLocale

@Composable
internal fun ReactiveLocaleScope(
    localeProvider: LocaleProvider,
    content: @Composable () -> Unit,
) {
    val currentLocale by localeProvider.locale.collectAsStateWithLifecycle()
    ObserveLocaleUpdates(localeProvider.locale)
    key(currentLocale) {
        content()
    }
}

@Composable
internal expect fun ObserveLocaleUpdates(locale: StateFlow<SportsRadarLocale>)

internal expect fun getDefaultLocale(): SportsRadarLocale