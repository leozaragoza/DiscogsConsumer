package com.tasks.discogsconsumer.domain.model

import com.google.gson.annotations.SerializedName

data class Stats(
    @SerializedName("community")
    val community: Community
)