package com.tasks.discogsconsumer.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tasks.discogsconsumer.di.annotations.Annotations
import com.tasks.discogsconsumer.di.factory.ViewModelFactory
import com.tasks.discogsconsumer.ui.viewmodel.ArtistReleasesViewModel
import com.tasks.discogsconsumer.ui.viewmodel.ReleaseDetailViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @Annotations.ViewModelKey(ArtistReleasesViewModel::class)
    abstract fun artistReleasesViewModel(viewModel: ArtistReleasesViewModel): ViewModel

    @Binds
    @IntoMap
    @Annotations.ViewModelKey(ReleaseDetailViewModel::class)
    abstract fun releaseDetailsViewModel(viewModel: ReleaseDetailViewModel): ViewModel
}