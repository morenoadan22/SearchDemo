package com.ngmatt.weedmapsandroidcodechallenge.search

import androidx.lifecycle.*
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Business
import com.ngmatt.weedmapsandroidcodechallenge.location.infrastructure.LocationRepository
import com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure.BusinessRepository
import com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure.SearchDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope

class SearchViewModel(
    private val repository: BusinessRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {

    val searchPagedListLiveData = initializeSearchListLiveData()
    private var searchDataSource: SearchDataSource? = null

    val searchTerm = MutableLiveData<String>()

    val suggestionsLiveData = searchTerm.switchMap {
        liveData {
            emit(repository.recentSearchTerms(it))
        }
    }

    val errorLiveData = MutableLiveData<Throwable>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorLiveData.postValue(throwable)
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
                    location = locationRepository,
                    currentQuery = searchTerm.value.orEmpty(),
                    scope = GlobalScope,
                    exceptionHandler = exceptionHandler
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