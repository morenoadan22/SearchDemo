package com.ngmatt.weedmapsandroidcodechallenge.location

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations ORDER BY date DESC LIMIT 1")
    suspend fun getLastUserLocation() : Coordinate

    @Insert
    suspend fun addLocation(coordinate: Coordinate)
}