package com.ngmatt.weedmapsandroidcodechallenge.di

import com.ngmatt.weedmapsandroidcodechallenge.location.LocationRepository
import com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure.BusinessRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        BusinessRepository(
            get(),
            get()
        )
    }
    factory { LocationRepository(get()) }
}