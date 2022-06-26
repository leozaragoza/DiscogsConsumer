package com.tasks.discogsconsumer.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tasks.discogsconsumer.domain.model.Release

@Dao
interface ReleaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<Release>)

    @Query("SELECT * FROM releases")
    fun getPagedReleases(): PagingSource<Int, Release>

    @Query("DELETE FROM releases")
    suspend fun clearAll()

    @Query("SELECT * FROM releases WHERE id = :id LIMIT 1")
    suspend fun getRelease(id: Int): Release?
}