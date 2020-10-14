package com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure

import com.ngmatt.weedmapsandroidcodechallenge.location.Coordinate

interface LocationSource {
    suspend fun getLastLocation() : Coordinate?
    suspend fun addLocation(coordinate: Coordinate)
}