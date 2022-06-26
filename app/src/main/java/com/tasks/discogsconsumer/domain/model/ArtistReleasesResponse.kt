package com.tasks.discogsconsumer.domain.model

import com.google.gson.annotations.SerializedName

data class ArtistReleasesResponse(
    @SerializedName("pagination")
    val pagination: Pagination,
    @SerializedName("releases")
    val releases: List<Release>
)