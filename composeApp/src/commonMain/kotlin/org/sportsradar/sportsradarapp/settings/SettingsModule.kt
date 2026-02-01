package org.sportsradar.sportsradarapp.settings

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.sportsradar.sportsradarapp.settings.presentation.LocaleViewModel

val settingsModule = module {
    viewModel { LocaleViewModel() }
}