package com.jsbs87.android.app.mapsmeep.presentation.di

import com.jsbs87.android.app.mapsmeep.presentation.network.NetworkHandler
import com.jsbs87.android.app.mapsmeep.presentation.ui.home.MapsActivity
import com.jsbs87.android.app.mapsmeep.presentation.ui.home.MapsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationModule = module {

    single { NetworkHandler(androidApplication()) }

    scope(named<MapsActivity>()) {
        viewModel { MapsViewModel(get()) }
    }

}
