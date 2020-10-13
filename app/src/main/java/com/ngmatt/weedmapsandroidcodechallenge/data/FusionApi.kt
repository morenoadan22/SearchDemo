package com.ngmatt.weedmapsandroidcodechallenge.data

import com.ngmatt.weedmapsandroidcodechallenge.data.model.Review
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FusionApi {
    @GET("businesses/search")
    suspend fun searchBusinesses(@Query("term") term: String, @Query("location") location: String) : BusinessResponse

    @GET("businesses/search")
    suspend fun searchBusinesses(@Query("term") term: String, @Query("latitude") latitude: String, @Query("longitude") longitude: String) : BusinessResponse

    @GET("businesses/search")
    suspend fun searchBusinesses(@Query("term") term: String, @Query("location") location: String, @Query("offset") offset: Int, @Query("limit") limit: Int): BusinessResponse

    @GET("businesses/search")
    suspend fun searchBusinesses(@Query("term") term: String, @Query("latitude") latitude: String, @Query("longitude") longitude: String, @Query("offset") offset: Int, @Query("limit") limit: Int): BusinessResponse

    @GET("businesses/{id}/reviews")
    suspend fun fetchBusinessReviews(@Path("id") businessId: String): ReviewsPayload
}