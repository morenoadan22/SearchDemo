package com.ngmatt.weedmapsandroidcodechallenge.location

class LocationRepository(
    private val locationDao: LocationDao
) {
    suspend fun getLastLocation() : Coordinate? = locationDao.getLastUserLocation()
    suspend fun addLocation(coordinate: Coordinate) = locationDao.addLocation(coordinate)
}