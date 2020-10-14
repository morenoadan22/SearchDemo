package com.ngmatt.weedmapsandroidcodechallenge.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LocationViewModel(
    private val locationClient: FusedLocationProviderClient,
    private val locationRepository: LocationRepository
): ViewModel() {

    val coordinateLiveData = MutableLiveData<Coordinate>()

    suspend fun permissionGranted() = refreshLastLocation()

    private suspend fun refreshLastLocation() {
        try {
            locationClient.lastLocation.addOnSuccessListener {
                GlobalScope.launch {
                    if(it != null) {
                        locationRepository.addLocation(Coordinate(Date(), it.latitude, it.longitude))
                        coordinateLiveData.postValue(locationRepository.getLastLocation())
                    }
                }
            }
            coordinateLiveData.postValue(locationRepository.getLastLocation())
        } catch (se: SecurityException) {
            // ignore for demo purposes
        }
    }

}