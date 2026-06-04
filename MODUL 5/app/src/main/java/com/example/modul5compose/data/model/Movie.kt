package com.example.modul5compose.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String?
) {

}