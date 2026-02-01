package org.sportsradar.sportsradarapp.common.localization

import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.StateFlow
import org.sportsradar.sportsradarapp.settings.presentation.SportsRadarLocale
import java.util.Locale

@Composable
internal actual fun ObserveLocaleUpdates(
    locale: StateFlow<SportsRadarLocale>,
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    LaunchedEffect(locale, configuration, context) {
        locale.collect { lang ->
            val newLocale = Locale.forLanguageTag(lang.isoCode)
            Locale.setDefault(newLocale)
            val newConfig = Configuration(configuration).apply {
                setLocale(newLocale)
            }
            context.updateConfiguration(configuration)
            LocalConfiguration.provides(newConfig)
        }
    }
}

private fun Context.updateConfiguration(
    configuration: Configuration,
) {
    val resources = resources
    resources.updateConfiguration(configuration, resources.displayMetrics)
}