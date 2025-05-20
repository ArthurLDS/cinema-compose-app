package com.angelisystems.cinemaapp.domain.model

import java.time.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val voteAverage: Double,
    val releaseDate: LocalDate?,
    val genreIds: List<Int>
) {
    fun isRecommended(): Boolean = voteAverage >= 7.0

    fun ratingType(): RatingType =  when {
        voteAverage >= 7.0 -> RatingType.RATING_HIGH
        voteAverage >= 5.0 -> RatingType.RATING_MEDIUM
        else -> RatingType.RATING_LOW
    }
}

enum class RatingType {
    RATING_HIGH, RATING_MEDIUM, RATING_LOW
}