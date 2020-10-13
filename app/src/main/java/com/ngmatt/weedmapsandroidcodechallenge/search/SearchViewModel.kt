package com.ngmatt.weedmapsandroidcodechallenge.search

import android.location.Location
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Business
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Coordinate
import kotlinx.coroutines.GlobalScope


class SearchViewModel(
    private val repository: BusinessRepository
) : ViewModel() {

    val searchPagedListLiveData = initializeSearchListLiveData()
    private var searchDataSource: SearchDataSource? = null

    val searchTerm = MutableLiveData<String>()
    private val coordinate = MutableLiveData<Coordinate>()

    fun setUserLocation(location: Location?) = location?.let {
        Log.d("Adan", "New Location detected $location")
        coordinate.postValue(Coordinate(it.latitude, it.longitude))
    }

    fun searchBusiness(term: String) {
        searchTerm.value = term
        searchDataSource?.invalidate()
    }

    private fun initializeSearchListLiveData(): LiveData<PagedList<Business>> {
        val config = PagedList.Config.Builder()
            .setPageSize(SEARCH_RESULT_LIMIT)
            .setPrefetchDistance(PREFETCH_DISTANCE)
            .setEnablePlaceholders(false)
            .build()

        val dataSource = object : DataSource.Factory<Int, Business>() {
            override fun create(): SearchDataSource {
                return SearchDataSource(
                    repository = repository,
                    coordinate = coordinate,
                    currentQuery = searchTerm.value.orEmpty(),
                    scope = GlobalScope
                ).also {
                    searchDataSource = it
                }
            }
        }

        return LivePagedListBuilder(dataSource, config).build()
    }

    companion object {
        const val SEARCH_RESULT_LIMIT = 15
        private const val PREFETCH_DISTANCE = 10
    }
}