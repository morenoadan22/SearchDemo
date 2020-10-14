package com.ngmatt.weedmapsandroidcodechallenge.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationRepository
import com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure.BusinessRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        BusinessRepository(
            get(),
            get()
        )
    }
    factory { provideLocationProvider(androidContext()) }
    factory {
        LocationRepository(
            get()
        )
    }
}

fun provideLocationProvider(context: Context) : FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}