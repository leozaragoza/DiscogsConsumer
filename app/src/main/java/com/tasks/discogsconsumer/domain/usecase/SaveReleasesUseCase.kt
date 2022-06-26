package com.tasks.discogsconsumer.domain.usecase

import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import javax.inject.Inject

class SaveReleasesUseCase @Inject constructor(private val releasesRepo: ReleasesRepo) {
    suspend fun saveReleases(releases: List<Release>) = releasesRepo.saveReleases(releases)
}