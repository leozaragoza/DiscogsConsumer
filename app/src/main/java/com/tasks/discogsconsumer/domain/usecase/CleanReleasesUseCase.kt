package com.tasks.discogsconsumer.domain.usecase

import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import javax.inject.Inject

class CleanReleasesUseCase @Inject constructor(private val releasesRepo: ReleasesRepo) {
    suspend fun cleanReleases() = releasesRepo.clearAll()
}