package com.tasks.discogsconsumer.domain.usecase

import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.db.repo.ReleasesRepo
import javax.inject.Inject

class GetReleaseDetailUseCase @Inject constructor(private val releasesRepo: ReleasesRepo) {
    suspend fun getRelease(id: Int): Release? = releasesRepo.getRelease(id)
}