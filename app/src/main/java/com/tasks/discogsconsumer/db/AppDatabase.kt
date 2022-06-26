package com.tasks.discogsconsumer.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tasks.discogsconsumer.db.dao.ReleaseDao
import com.tasks.discogsconsumer.domain.model.Release

@Database(entities = [Release::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun releaseDao(): ReleaseDao
}