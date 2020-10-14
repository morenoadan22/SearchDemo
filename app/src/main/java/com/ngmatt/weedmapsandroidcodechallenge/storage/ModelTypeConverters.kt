package com.ngmatt.weedmapsandroidcodechallenge.storage

import androidx.room.TypeConverter
import java.util.*

class ModelTypeConverters {

    @TypeConverter
    fun dateToTimeStamp(date: Date) : Long = date.time

    @TypeConverter
    fun fromMillis(millis: Long): Date = Date(millis)

}