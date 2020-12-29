package com.jsbs87.android.app.mapsmeep

import android.app.Application
import com.jsbs87.android.app.mapsmeep.data.di.dataModule
import com.jsbs87.android.app.mapsmeep.domain.di.domainModule

import com.jsbs87.android.app.mapsmeep.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MapsMeepApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(listOf( presentationModule, domainModule, dataModule))
        }
    }
}