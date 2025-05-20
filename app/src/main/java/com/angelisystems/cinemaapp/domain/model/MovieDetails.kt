package com.angelisystems.cinemaapp.domain.model

import java.time.LocalDate

data class MovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: LocalDate?,
    val runtime: Int?,
    val genres: List<String>,
    val voteCount: Int,
    val status: String,
    val originalLanguage: String
) 