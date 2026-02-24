package org.michorga.everythinginone

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.michorga.everythinginone.data.androidModule
import org.michorga.everythinginone.di.sharedModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(androidModule, sharedModule)
        }
    }
}