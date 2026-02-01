package org.sportsradar.sportsradarapp.settings.presentation

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.sportsradar.sportsradarapp.common.localization.LocaleProvider
import org.sportsradar.sportsradarapp.common.localization.getDefaultLocale
import org.sportsradar.sportsradarapp.common.utils.DefaultSharingStarted
import org.sportsradar.sportsradarapp.storage.Storage
import org.sportsradar.sportsradarapp.storage.getBlocking
import org.sportsradar.sportsradarapp.storage.set
import org.sportsradar.sportsradarapp.storage.subscribeOrDefault

class LocaleStorage(private val storage: Storage) {
    fun getLocaleFlow(): Flow<SportsRadarLocale> {
        return storage.subscribeOrDefault<SportsRadarLocale>(LOCALE_STORAGE_KEY) {
            getDefaultLocale()
        }
    }

    fun getLocale(): SportsRadarLocale {
        return storage.getBlocking<SportsRadarLocale>(LOCALE_STORAGE_KEY)
            ?: getDefaultLocale()
    }

    suspend fun updateLocale(locale: SportsRadarLocale) {
        storage[LOCALE_STORAGE_KEY] = locale
    }

    private companion object {
        const val LOCALE_STORAGE_KEY = "APP_LOCALE"
    }
}

@Stable
class LocaleViewModel(
    private val localeStorage: LocaleStorage,
) : LocaleProvider, ViewModel() {
    override val locale = localeStorage.getLocaleFlow().stateIn(
        scope = viewModelScope,
        initialValue = localeStorage.getLocale(),
        started = DefaultSharingStarted
    )

    private var localeUpdateJob: Job? = null
    override fun updateLocale(locale: SportsRadarLocale) {
        if (localeUpdateJob?.isActive == true) {
            return
        }
        localeUpdateJob = viewModelScope.launch {
            localeStorage.updateLocale(locale)
        }
    }
}