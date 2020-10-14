package com.ngmatt.weedmapsandroidcodechallenge.location

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "locations")
data class Coordinate(
    @PrimaryKey val date: Date,
    val latitude: Double,
    val longitude: Double
) {
    val latitudeString: String get() = "$latitude"
    val longitudeString: String get() = "$longitude"
}