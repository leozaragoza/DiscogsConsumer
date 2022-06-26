package com.tasks.discogsconsumer.domain.usecase

import androidx.paging.PagingSource
import com.tasks.discogsconsumer.domain.model.ArtistReleasesResponse
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import javax.inject.Inject

class GetReleasesUseCase @Inject constructor(private val releasesRepo: ReleasesRepo) {
    suspend fun getReleases(artistId: Int, page: Int): ArtistReleasesResponse = releasesRepo.fetchReleases(artistId, page)

    fun getPagedReleases() : PagingSource<Int, Release> = releasesRepo.getPagedReleases()
}