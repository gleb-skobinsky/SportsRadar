package org.sportsradar.sportsradarapp.init

import org.sportsradar.sportsradarapp.init.presentation.InitStateController
import org.koin.dsl.module

val initModule = module {
    single { InitStateController() }
}