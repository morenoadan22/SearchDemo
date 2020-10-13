package com.ngmatt.weedmapsandroidcodechallenge.koin

import com.ngmatt.weedmapsandroidcodechallenge.search.SearchBusinessFragment
import org.koin.dsl.module

val fragmentModule =  module {
    factory { SearchBusinessFragment() }
}