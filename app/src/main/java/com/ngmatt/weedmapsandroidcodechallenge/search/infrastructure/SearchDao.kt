package com.ngmatt.weedmapsandroidcodechallenge.search.infrastructure

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ngmatt.weedmapsandroidcodechallenge.data.model.Business
import com.ngmatt.weedmapsandroidcodechallenge.data.model.QueryItem

@Dao
interface SearchDao {
    @Query("SELECT term FROM searches WHERE term LIKE :query")
    suspend fun findSuggestions(query: String): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(query: QueryItem)

}