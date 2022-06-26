package com.tasks.discogsconsumer.di

import com.tasks.discogsconsumer.db.dao.ReleaseDao
import com.tasks.discogsconsumer.network.api.ReleasesService
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import com.tasks.discogsconsumer.db.repo.ReleasesRepoImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideReleasesRepo(releasesService: ReleasesService, releaseDao: ReleaseDao): ReleasesRepo =
        ReleasesRepoImpl(releasesService, releaseDao)
}