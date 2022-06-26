package com.tasks.discogsconsumer.domain.model

import com.google.gson.annotations.SerializedName

data class Community(
    @SerializedName("in_collection")
    val inCollection: Int,
    @SerializedName("in_wantlist")
    val inWantlist: Int
)