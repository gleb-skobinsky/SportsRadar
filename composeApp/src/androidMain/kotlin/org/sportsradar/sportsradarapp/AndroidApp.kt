package org.sportsradar.sportsradarapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.sportsradar.sportsradarapp.di.configureModules

class AndroidApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@AndroidApp)
            configureModules()
        }
    }
}