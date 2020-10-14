package com.ngmatt.weedmapsandroidcodechallenge

import com.ngmatt.weedmapsandroidcodechallenge.data.ReviewsPayload
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Review
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ReviewsPayloadTest {

    private val mockReviews: List<Review> = listOf(
        Review("1", 3.4f, "test 1", ""),
        Review("2", 3.5f, "test 2", ""),
        Review("3", 3.6f, "test 3", ""),
        Review("4", 3.7f, "test 4", ""),
        Review("5", 3.8f, "test 5", ""),
        Review("6", 5.0f, "test 6", ""),
        Review("7", 4.0f, "test 7", ""),
        Review("8", 4.1f, "test 8", ""),
        Review("9", 4.2f, "test 9", ""),
        Review("10", 4.3f, "test 10", "")
    )

    private val reviewPayload = ReviewsPayload(10, mockReviews)

    @Test
    fun `test top review from reviews`() {
        assertNotNull(reviewPayload.topRatedReview)
        assertEquals("test 6", reviewPayload.topRatedReview)
    }
}