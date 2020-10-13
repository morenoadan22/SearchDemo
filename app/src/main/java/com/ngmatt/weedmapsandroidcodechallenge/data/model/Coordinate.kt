package com.ngmatt.weedmapsandroidcodechallenge.data.model

data class Coordinate(
    val latitude: Double,
    val longitude: Double
) {
    val latitudeString: String get() = "$latitude"
    val longitudeString: String get() = "$longitude"
}