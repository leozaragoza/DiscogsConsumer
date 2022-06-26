package com.tasks.discogsconsumer.network

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.domain.usecase.CleanReleasesUseCase
import com.tasks.discogsconsumer.domain.usecase.GetReleasesUseCase
import com.tasks.discogsconsumer.domain.usecase.SaveReleasesUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ReleasesRemoteMediator @Inject constructor(
    private val getReleasesUseCase: GetReleasesUseCase,
    private val saveReleasesUseCase: SaveReleasesUseCase,
    private val cleanReleasesUseCase: CleanReleasesUseCase
) : RemoteMediator<Int, Release>() {

    private var page = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Release>
    ): MediatorResult {
        return try {
            when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    state.lastItemOrNull()
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    page
                }
            }

            val response = getReleasesUseCase.getReleases(1, page = page)
            ++page

            if (loadType == LoadType.REFRESH) {
                cleanReleasesUseCase.cleanReleases()
            }

            saveReleasesUseCase.saveReleases(response.releases)

            MediatorResult.Success(
                endOfPaginationReached = response.pagination.page > response.pagination.pages
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}