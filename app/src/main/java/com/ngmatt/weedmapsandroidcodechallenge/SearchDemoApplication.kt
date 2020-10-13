package com.ngmatt.weedmapsandroidcodechallenge

import android.app.Application
import com.ngmatt.weedmapsandroidcodechallenge.di.fragmentModule
import com.ngmatt.weedmapsandroidcodechallenge.di.networkModule
import com.ngmatt.weedmapsandroidcodechallenge.di.repositoryModule
import com.ngmatt.weedmapsandroidcodechallenge.di.viewModelModule
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