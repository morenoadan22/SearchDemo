package com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure

import com.ngmatt.weedmapsandroidcodechallenge.location.Coordinate

class LocationRepository(
    private val locationDao: LocationDao
) : LocationSource {
    override suspend fun getLastLocation() : Coordinate? = locationDao.getLastUserLocation()
    override suspend fun addLocation(coordinate: Coordinate) = locationDao.addLocation(coordinate)
}