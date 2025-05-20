package com.angelisystems.cinemaapp.data.model.mapper

import com.angelisystems.cinemaapp.data.model.dto.FavoriteMovieDto
import com.angelisystems.cinemaapp.domain.model.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun Movie.toFavoriteEntity(): FavoriteMovieDto = FavoriteMovieDto(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate?.format(DateTimeFormatter.ISO_DATE)
)

fun FavoriteMovieDto.toDomainModel(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate?.let {
        try { LocalDate.parse(it, DateTimeFormatter.ISO_DATE) } catch (e: Exception) { null }
    },
    genreIds = emptyList() // Favoritos não armazenam gêneros
) 