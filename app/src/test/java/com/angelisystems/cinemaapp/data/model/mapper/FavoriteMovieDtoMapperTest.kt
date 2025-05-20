package com.angelisystems.cinemaapp.data.model.mapper

import com.angelisystems.cinemaapp.data.model.dto.FavoriteMovieDto
import com.angelisystems.cinemaapp.domain.model.Movie
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FavoriteMovieDtoMapperTest {

    @Test
    fun `FavoriteMovieDto toDomainModel should correctly map all fields`() {
        // Arrange
        val favoriteMovieDto = FavoriteMovieDto(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = "2023-01-15"
        )
        
        // Act
        val movie = favoriteMovieDto.toDomainModel()
        
        // Assert
        assertThat(movie.id).isEqualTo(favoriteMovieDto.id)
        assertThat(movie.title).isEqualTo(favoriteMovieDto.title)
        assertThat(movie.overview).isEqualTo(favoriteMovieDto.overview)
        assertThat(movie.posterPath).isEqualTo(favoriteMovieDto.posterPath)
        assertThat(movie.backdropPath).isEqualTo(favoriteMovieDto.backdropPath)
        assertThat(movie.voteAverage).isEqualTo(favoriteMovieDto.voteAverage)
        
        // Verificar a data
        val expectedDate = LocalDate.parse("2023-01-15", DateTimeFormatter.ISO_DATE)
        assertThat(movie.releaseDate).isEqualTo(expectedDate)
        
        // GenreIds deve ser uma lista vazia
        assertThat(movie.genreIds).isEmpty()
    }
    
    @Test
    fun `FavoriteMovieDto toDomainModel should handle null releaseDate`() {
        // Arrange
        val favoriteMovieDto = FavoriteMovieDto(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = null
        )
        
        // Act
        val movie = favoriteMovieDto.toDomainModel()
        
        // Assert
        assertThat(movie.releaseDate).isNull()
    }
    
    @Test
    fun `Movie toFavoriteEntity should correctly map all fields`() {
        // Arrange
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = LocalDate.of(2023, 1, 15),
            genreIds = listOf(1, 2, 3)
        )
        
        // Act
        val favoriteMovieDto = movie.toFavoriteEntity()
        
        // Assert
        assertThat(favoriteMovieDto.id).isEqualTo(movie.id)
        assertThat(favoriteMovieDto.title).isEqualTo(movie.title)
        assertThat(favoriteMovieDto.overview).isEqualTo(movie.overview)
        assertThat(favoriteMovieDto.posterPath).isEqualTo(movie.posterPath)
        assertThat(favoriteMovieDto.backdropPath).isEqualTo(movie.backdropPath)
        assertThat(favoriteMovieDto.voteAverage).isEqualTo(movie.voteAverage)
        
        // Verificar a data
        val expectedDateString = "2023-01-15"
        assertThat(favoriteMovieDto.releaseDate).isEqualTo(expectedDateString)
    }
    
    @Test
    fun `Movie toFavoriteEntity should handle null releaseDate`() {
        // Arrange
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = null,
            genreIds = listOf(1, 2, 3)
        )
        
        // Act
        val favoriteMovieDto = movie.toFavoriteEntity()
        
        // Assert
        assertThat(favoriteMovieDto.releaseDate).isNull()
    }
} 