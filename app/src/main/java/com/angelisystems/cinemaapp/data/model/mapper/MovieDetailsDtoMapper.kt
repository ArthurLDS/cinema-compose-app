package com.angelisystems.cinemaapp.data.model.mapper

import com.angelisystems.cinemaapp.data.model.dto.MovieDetailsDto
import com.angelisystems.cinemaapp.domain.model.MovieDetails
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

fun MovieDetailsDto.toDomainModel(): MovieDetails {
    val parsedReleaseDate = try {
        releaseDate?.let {
            LocalDate.parse(it, DateTimeFormatter.ISO_DATE)
        }
    } catch (e: DateTimeParseException) {
        null
    }

    return MovieDetails(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage,
        releaseDate = parsedReleaseDate,
        runtime = runtime,
        genres = genres.map { it.name },
        voteCount = voteCount,
        status = status,
        originalLanguage = originalLanguage
    )
}