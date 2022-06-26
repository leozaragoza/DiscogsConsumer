package com.tasks.discogsconsumer.db.repo

import com.tasks.discogsconsumer.common.Constants.Companion.DEFAULT_ARTIST_ID
import com.tasks.discogsconsumer.db.dao.ReleaseDao
import com.tasks.discogsconsumer.network.api.ReleasesService
import com.tasks.discogsconsumer.domain.model.Release
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReleasesRepoImpl @Inject constructor(private val releasesService: ReleasesService,
                                           private val releasesDao: ReleaseDao): ReleasesRepo {
    override suspend fun fetchReleases(artistId: Int, page: Int) =
        withContext(Dispatchers.IO) { releasesService.fetchReleases(DEFAULT_ARTIST_ID, page, 75) }

    override fun getPagedReleases() = releasesDao.getPagedReleases()

    override suspend fun saveReleases(releases: List<Release>) = withContext(Dispatchers.IO) {
        releasesDao.insertAll(releases)
    }

    override suspend fun getRelease(id: Int): Release? = releasesDao.getRelease(id)

    override suspend fun clearAll() = releasesDao.clearAll()
}