package com.ngmatt.weedmapsandroidcodechallenge.location

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LocationViewModel(
    context: Context,
    private val locationRepository: LocationRepository
): ViewModel() {

    private val locationClient : FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    val coordinateLiveData = MutableLiveData<Coordinate>()

    suspend fun permissionGranted() = refreshLastLocation()

    private suspend fun refreshLastLocation() {
        try {
            locationClient.lastLocation.addOnSuccessListener {
                GlobalScope.launch {
                    locationRepository.addLocation(
                        Coordinate(Date(), it.latitude, it.longitude)
                    )
                    coordinateLiveData.postValue(locationRepository.getLastLocation())
                }
            }
            coordinateLiveData.postValue(locationRepository.getLastLocation())
        } catch (se: SecurityException) {

        }
    }

}