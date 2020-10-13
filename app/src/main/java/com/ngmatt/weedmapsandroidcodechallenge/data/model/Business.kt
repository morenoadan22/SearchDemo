package com.ngmatt.weedmapsandroidcodechallenge.data.model

data class Business(
    val id: String,
    var rating: Float?,
    var price: String?,
    var phone: String?,
    var alias: String?,
    var is_closed: Boolean?,
    var categories: List<Category>?,
    var review_count: Int?,
    var name: String?,
    var url: String?,
    var coordinates: Coordinate,
    var image_url: String?,
    var location: Location?,
    var distance: Float?,
    var transactions: List<String>?
) {

    var review: String? = null
}
