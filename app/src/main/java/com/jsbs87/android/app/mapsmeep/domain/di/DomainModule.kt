package com.jsbs87.android.app.mapsmeep.domain.di

import com.jsbs87.android.app.mapsmeep.domain.interactors.GetResourcesUseCase
import org.koin.dsl.module


val domainModule = module {
    factory { GetResourcesUseCase(get()) }
}
