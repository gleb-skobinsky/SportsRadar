package org.sportsradar.sportsradarapp.di

import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.sportsradar.sportsradarapp.init.presentation.InitStateController

@Suppress("Unused") // used in swift
object InitControllerProvider : KoinComponent {
    val controller: InitStateController
        get() = get()
}