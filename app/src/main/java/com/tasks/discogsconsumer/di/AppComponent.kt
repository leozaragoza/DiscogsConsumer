package com.tasks.discogsconsumer.di

import com.tasks.discogsconsumer.application.DiscogsConsumerApplication
import com.tasks.discogsconsumer.ui.fragment.ReleaseDetailFragment
import com.tasks.discogsconsumer.ui.fragment.ReleasesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class, NetworkModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {

    fun inject(application: DiscogsConsumerApplication)

    fun inject(releasesListFragment: ReleasesListFragment)

    fun inject(releaseDetailFragment: ReleaseDetailFragment)
}