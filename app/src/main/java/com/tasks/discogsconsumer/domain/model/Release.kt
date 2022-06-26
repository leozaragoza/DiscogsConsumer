package com.tasks.discogsconsumer.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "releases")
data class Release(
    @SerializedName("artist")
    val artist: String,
    @SerializedName("id")
    @PrimaryKey
    val id: Int,
    @SerializedName("main_release")
    val main_release: Int,
    @SerializedName("resource_url")
    val resourceUrl: String,
    @SerializedName("role")
    val role: String,
    //@SerializedName("stats")
    //val stats: Stats,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("year")
    val year: Int,
    @SerializedName("label")
    val label: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("format")
    val format: String?
    )