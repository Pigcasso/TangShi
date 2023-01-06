package com.danliren.apps.tangshi.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.danliren.apps.tangshi.data.Footprint

@Dao
interface FootprintDao {

    @Query("SELECT * FROM footprint WHERE type=:type ORDER BY created_time DESC")
    suspend fun loadAllByType(type: Int): List<Footprint>

    @Query("SELECT * FROM footprint WHERE poetryId=:poetryId AND type=:type")
    suspend fun findByPoetryIdAndType(poetryId: Int, type: Int): Footprint?

    @Insert
    suspend fun insertAll(vararg footprint: Footprint)

    @Query("DELETE FROM footprint WHERE poetryId=:poetryId AND type=:type")
    suspend fun deleteByPoetryIdAndType(poetryId: Int, type: Int)
}