package com.ngmatt.weedmapsandroidcodechallenge.di

import com.ngmatt.weedmapsandroidcodechallenge.location.LocationViewModel
import com.ngmatt.weedmapsandroidcodechallenge.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val viewModelModule = module {
    factory { LocationViewModel(androidContext(), get()) }
    factory { SearchViewModel(get(), get()) }
}