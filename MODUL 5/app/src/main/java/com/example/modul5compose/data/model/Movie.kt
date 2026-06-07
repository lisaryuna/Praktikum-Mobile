package com.example.modul5compose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    @SerialName("results")
    val results: List<Movie>
)

@Serializable
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @SerialName("id")
    val id: Int,

    @SerialName("title")
    val title: String,

    @SerialName("overview")
    val overview: String,

    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String?,

    @SerialName("vote_average")
    val voteAverage: Double?
) {

}