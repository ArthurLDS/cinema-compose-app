package com.angelisystems.cinemaapp.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerialName("poster_path") val posterPath: String?,
    @SerialName("backdrop_path") val backdropPath: String?,
    @SerialName("vote_average") val voteAverage: Double,
    @SerialName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<GenreDto>,
    @SerialName("vote_count") val voteCount: Int,
    val status: String,
    @SerialName("original_language") val originalLanguage: String
)

@Serializable
data class GenreDto(
    val id: Int,
    val name: String
) 