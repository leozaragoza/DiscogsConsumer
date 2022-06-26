package com.tasks.discogsconsumer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tasks.discogsconsumer.domain.usecase.CleanReleasesUseCase
import com.tasks.discogsconsumer.domain.usecase.GetReleasesUseCase
import com.tasks.discogsconsumer.network.ReleasesRemoteMediator
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtistReleasesViewModel @Inject constructor(
    private val getReleasesUseCase: GetReleasesUseCase,
    private val cleanReleasesUseCase: CleanReleasesUseCase,
    private val releasesRemoteMediator: ReleasesRemoteMediator): ViewModel() {

    //with no persitence
    /*val pagedFlow = Pager(
        PagingConfig(pageSize = 75)
    ) {
        ArtistReleasesPagingSource(getArtistReleasesUseCase)
    }.flow.cachedIn(viewModelScope)*/

    @ExperimentalPagingApi
    val pagedFlow = Pager(
        config = PagingConfig(pageSize = 75),
        remoteMediator = releasesRemoteMediator
    ) {
        getReleasesUseCase.getPagedReleases()
    }.flow.cachedIn(viewModelScope)

    fun cleanReleases() {
        viewModelScope.launch {
            cleanReleasesUseCase.cleanReleases()
        }
    }
}