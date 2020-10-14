package com.ngmatt.weedmapsandroidcodechallenge.di

import android.app.Application
import androidx.room.Room
import com.ngmatt.weedmapsandroidcodechallenge.location.LocationDao
import com.ngmatt.weedmapsandroidcodechallenge.storage.BusinessDatabase
import com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure.SearchDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    fun provideDatabase(application: Application): BusinessDatabase {
        return Room.databaseBuilder(application, BusinessDatabase::class.java, "businesses")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideSearchDao(database: BusinessDatabase): SearchDao = database.searchDao
    fun provideLocationDao(database: BusinessDatabase): LocationDao = database.locationDao

    single { provideDatabase(androidApplication()) }
    single { provideSearchDao(get()) }
    single { provideLocationDao(get()) }
}