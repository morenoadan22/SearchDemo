package com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure

import androidx.paging.PositionalDataSource
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Business
import com.ngmatt.weedmapsandroidcodechallenge.location.Coordinate
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationRepository
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

class SearchDataSource(
    private val currentQuery: String,
    private val location: LocationSource,
    private val repository: BusinessSource,
    private val scope: CoroutineScope,
    private val exceptionHandler: CoroutineExceptionHandler
): PositionalDataSource<Business>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Business>) {
        scope.launch(exceptionHandler) {
            val lastCoordinate = location.getLastLocation() ?: Coordinate(Date(), 0.0, 0.0)
            val result = repository.search(currentQuery, lastCoordinate.latitudeString, lastCoordinate.longitudeString, params.startPosition, params.loadSize)
            callback.onResult(result.businesses)
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Business>) {
        scope.launch(exceptionHandler) {
            val lastCoordinate = location.getLastLocation() ?: Coordinate(Date(), 0.0, 0.0)
            val result = repository.search(currentQuery, lastCoordinate.latitudeString, lastCoordinate.longitudeString)
            callback.onResult(result.businesses, 0)
        }
    }


}