package com.ngmatt.weedmapsandroidcodechallenge

import android.app.Application
import com.ngmatt.weedmapsandroidcodechallenge.koin.fragmentModule
import com.ngmatt.weedmapsandroidcodechallenge.koin.networkModule
import com.ngmatt.weedmapsandroidcodechallenge.koin.repositoryModule
import com.ngmatt.weedmapsandroidcodechallenge.koin.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SearchDemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SearchDemoApplication)
            modules(listOf(
                networkModule,
                repositoryModule,
                viewModelModule,
                fragmentModule
            ))
        }
    }
}