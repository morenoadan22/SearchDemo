package com.ngmatt.weedmapsandroidcodechallenge.di

import com.ngmatt.weedmapsandroidcodechallenge.search.SearchViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { SearchViewModel(get()) }
}