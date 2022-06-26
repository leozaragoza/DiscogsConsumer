package com.tasks.discogsconsumer.ui.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tasks.discogsconsumer.domain.model.Release
import com.tasks.discogsconsumer.domain.usecase.GetReleasesUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ArtistReleasesPagingSource @Inject constructor(private val getReleasesUseCase: GetReleasesUseCase) :
    PagingSource<Int, Release>() {

    override fun getRefreshKey(state: PagingState<Int, Release>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Release> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = getReleasesUseCase.getReleases(1, nextPageNumber)

            return LoadResult.Page(
                data = response.releases,
                prevKey = if (response.pagination.page > 1) response.pagination.page.minus(1) else null,
                nextKey = if (response.pagination.pages > response.pagination.page) response.pagination.page.plus(1) else null
            )
        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}