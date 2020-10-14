package com.ngmatt.weedmapsandroidcodechallenge.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ngmatt.weedmapsandroidcodechallenge.data.model.QueryItem
import com.ngmatt.weedmapsandroidcodechallenge.location.Coordinate
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationDao
import com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure.SearchDao

@Database(entities = [QueryItem::class, Coordinate::class], version = 1, exportSchema = false)
@TypeConverters(ModelTypeConverters::class)
abstract class BusinessDatabase : RoomDatabase() {

  abstract val searchDao: SearchDao
  abstract val locationDao: LocationDao
}