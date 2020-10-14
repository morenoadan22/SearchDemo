package com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ngmatt.weedmapsandroidcodechallenge.location.Coordinate

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations ORDER BY date DESC LIMIT 1")
    suspend fun getLastUserLocation() : Coordinate

    @Insert
    suspend fun addLocation(coordinate: Coordinate)
}