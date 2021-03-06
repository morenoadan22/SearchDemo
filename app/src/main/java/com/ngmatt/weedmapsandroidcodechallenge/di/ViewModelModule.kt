package com.ngmatt.weedmapsandroidcodechallenge.di

import com.ngmatt.weedmapsandroidcodechallenge.location.LocationViewModel
import com.ngmatt.weedmapsandroidcodechallenge.search.SearchViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { LocationViewModel(get(), get()) }
    factory { SearchViewModel(get(), get()) }
}