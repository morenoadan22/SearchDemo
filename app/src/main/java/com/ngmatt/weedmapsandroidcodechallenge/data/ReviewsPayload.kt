package com.ngmatt.weedmapsandroidcodechallenge.data

import com.ngmatt.weedmapsandroidcodechallenge.data.model.Review

data class ReviewsPayload(
    val total: Int,
    val reviews: List<Review>
) {
    val topRatedReview: String?
        get() = reviews.maxByOrNull { it.rating ?: 0F }?.text
}