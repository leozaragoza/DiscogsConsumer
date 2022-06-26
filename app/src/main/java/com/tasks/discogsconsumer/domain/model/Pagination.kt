package com.tasks.discogsconsumer.domain.model

import com.google.gson.annotations.SerializedName

data class Pagination(
    @SerializedName("items")
    val items: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("urls")
    val urls: Urls
)