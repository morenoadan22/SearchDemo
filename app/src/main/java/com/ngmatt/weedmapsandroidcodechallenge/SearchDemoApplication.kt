package com.ngmatt.weedmapsandroidcodechallenge

import android.app.Application
import com.ngmatt.weedmapsandroidcodechallenge.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SearchDemoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SearchDemoApplication)
            modules(listOf(
                networkModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
                fragmentModule
            ))
        }
    }
}