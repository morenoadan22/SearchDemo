package com.ngmatt.weedmapsandroidcodechallenge.search

import androidx.lifecycle.LiveData
import androidx.paging.PositionalDataSource
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Business
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Coordinate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SearchDataSource(
    private val currentQuery: String,
    private val coordinate: LiveData<Coordinate>,
    private val repository: BusinessRepository,
    private val scope: CoroutineScope,
): PositionalDataSource<Business>() {

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Business>) {
        scope.launch {
            val result = repository.search(currentQuery, coordinate.value?.latitudeString ?: "0", coordinate.value?.longitudeString ?: "0", params.startPosition, params.loadSize)
            callback.onResult(result.businesses)
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Business>) {
        scope.launch {
            val result = repository.search(currentQuery, coordinate.value?.latitudeString ?: "0", coordinate.value?.longitudeString ?: "0")
            callback.onResult(result.businesses, 0)
        }
    }


}