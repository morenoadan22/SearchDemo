package com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure

import com.ngmatt.weedmapsandroidcodechallenge.data.BusinessResponse

interface BusinessSource {
    suspend fun recentSearchTerms(query: String): List<String>
    suspend fun search(term: String, location: String) : BusinessResponse
    suspend fun search(term: String, latitude: String, longitude: String, offset: Int = 0, limit: Int = 10): BusinessResponse
}