package com.tasks.discogsconsumer.network.api

import com.tasks.discogsconsumer.domain.model.ArtistReleasesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReleasesService {
    @GET("artists/{artistId}/releases")
    suspend fun fetchReleases(@Path("artistId") artistId: Int,
                              @Query("page") pageNumber: Int,
                              @Query("per_page") perPage: Int): ArtistReleasesResponse
}