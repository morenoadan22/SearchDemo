package com.ngmatt.weedmapsandroidcodechallenge.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "searches")
data class QueryItem(
    @PrimaryKey val term: String,
    val date: Date
)