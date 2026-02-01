package org.sportsradar.sportsradarapp.settings

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.sportsradar.sportsradarapp.common.localization.LocaleInitializer
import org.sportsradar.sportsradarapp.common.localization.getLocaleInitializer
import org.sportsradar.sportsradarapp.settings.presentation.LocaleStorage
import org.sportsradar.sportsradarapp.settings.presentation.LocaleViewModel

val settingsModule = module {
    single { LocaleStorage(get()) }
    viewModel { LocaleViewModel(get()) }
    single<LocaleInitializer> { getLocaleInitializer() }
}