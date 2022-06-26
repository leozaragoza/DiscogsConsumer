package com.tasks.discogsconsumer.di

import android.content.Context
import androidx.room.Room
import com.tasks.discogsconsumer.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "discogs-db"
     ).build()

    @Provides
    @Singleton
    fun provideReleasesDao(appDatabase: AppDatabase) = appDatabase.releaseDao()
}