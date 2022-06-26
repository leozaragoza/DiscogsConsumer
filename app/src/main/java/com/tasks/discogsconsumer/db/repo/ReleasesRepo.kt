package com.tasks.discogsconsumer.db.repo

import androidx.paging.PagingSource
import com.tasks.discogsconsumer.domain.model.ArtistReleasesResponse
import com.tasks.discogsconsumer.domain.model.Release

interface ReleasesRepo: Repo {
    suspend fun fetchReleases(artistId: Int, page: Int): ArtistReleasesResponse
    fun getPagedReleases(): PagingSource<Int, Release>
    suspend fun saveReleases(releases: List<Release>)
    suspend fun getRelease(id: Int) : Release?
    suspend fun clearAll()
}