package com.ngmatt.weedmapsandroidcodechallenge.di

import com.ngmatt.weedmapsandroidcodechallenge.search.BusinessRepository
import org.koin.dsl.module

val repositoryModule = module {
    factory { BusinessRepository(get()) }
}