package com.jsbs87.android.app.mapsmeep.data.di

import com.jsbs87.android.app.mapsmeep.BuildConfig
import com.jsbs87.android.app.mapsmeep.data.api.MapsMeepApiService
import com.jsbs87.android.app.mapsmeep.data.repository.MapsMeepRepositoryImp
import com.jsbs87.android.app.mapsmeep.domain.repository.MapsMeepRepository

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getOkHttpClient(httpLoggingInterceptor()))
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(MapsMeepApiService::class.java)
    } bind MapsMeepApiService::class

    single {
        MapsMeepRepositoryImp(
            apiService = get(),
            networkHandler = get())
    } bind MapsMeepRepository::class
}

fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
}

fun httpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    } else {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
    }
    return httpLoggingInterceptor

}

