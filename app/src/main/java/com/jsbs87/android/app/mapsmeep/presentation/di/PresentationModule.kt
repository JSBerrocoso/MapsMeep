package com.jsbs87.android.app.mapsmeep.presentation.di

import com.google.android.gms.maps.model.LatLng
import com.jsbs87.android.app.mapsmeep.BuildConfig
import com.jsbs87.android.app.mapsmeep.presentation.network.NetworkHandler
import com.jsbs87.android.app.mapsmeep.presentation.ui.home.MapsActivity
import com.jsbs87.android.app.mapsmeep.presentation.ui.home.MapsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val presentationModule = module {

    single { NetworkHandler(androidApplication()) }

    scope(named<MapsActivity>()) {
        viewModel { MapsViewModel(get()) }

    }

    single {
        LatLng(
            BuildConfig.DEFAULT_LAT_LOCATION.toDouble(),
            BuildConfig.DEFAULT_LNG_LOCATION.toDouble()
        )
    } bind LatLng::class

}
