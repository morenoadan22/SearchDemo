package com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure

import com.ngmatt.weedmapsandroidcodechallenge.data.BusinessResponse
import com.ngmatt.weedmapsandroidcodechallenge.data.FusionApi
import com.ngmatt.weedmapsandroidcodechallenge.data.model.QueryItem
import java.util.*

class BusinessRepository(
    private val fusionApi: FusionApi,
    private val searchDao: SearchDao,
) {
    suspend fun recentSearchTerms(query: String) = searchDao.findSuggestions("%$query%")
    suspend fun search(term: String, location: String) = fusionApi.searchBusinesses(term, location)
    suspend fun search(term: String, latitude: String, longitude: String, offset: Int = 0, limit: Int = 10): BusinessResponse {
            val businessResponse = fusionApi.searchBusinesses(
                term,
                latitude,
                longitude,
                offset = offset,
                limit = limit
            )
            businessResponse.businesses.map {
                it.review = fusionApi.fetchBusinessReviews(it.id).topRatedReview
            }
            searchDao.add(QueryItem(term, Date()))
            return businessResponse
    }
}