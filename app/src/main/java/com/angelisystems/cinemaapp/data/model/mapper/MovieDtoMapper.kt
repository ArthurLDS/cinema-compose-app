package com.angelisystems.cinemaapp.data.model.mapper

import com.angelisystems.cinemaapp.data.model.dto.MovieDto
import com.angelisystems.cinemaapp.domain.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun MovieDto.toDomainModel(): Movie {
    val parsedReleaseDate = try {
        releaseDate?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
        }
    } catch (e: DateTimeParseException) {
        null
    }

    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = parsedReleaseDate,
        genreIds = genreIds
    )
}