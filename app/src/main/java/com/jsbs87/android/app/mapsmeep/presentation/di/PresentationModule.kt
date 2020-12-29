package com.jsbs87.android.app.mapsmeep.presentation.di

import com.jsbs87.android.app.mapsmeep.presentation.network.NetworkHandler
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val presentationModule = module {

    single { NetworkHandler(androidApplication()) }

//    scope(named<MoviesFragment>()) {
//        viewModel { MoviesViewModel(get()) }
//    }

}
