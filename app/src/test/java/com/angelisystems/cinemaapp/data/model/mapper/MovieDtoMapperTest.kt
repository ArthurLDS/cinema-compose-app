package com.angelisystems.cinemaapp.data.model.mapper

import com.angelisystems.cinemaapp.data.model.dto.GenreDto
import com.angelisystems.cinemaapp.data.model.dto.MovieDetailsDto
import com.angelisystems.cinemaapp.data.model.dto.MovieDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDtoMapperTest {

    @Test
    fun `MovieDto toDomainModel should correctly map all fields`() {
        // Arrange
        val movieDto = MovieDto(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = "2023-01-15",
            genreIds = listOf(1, 2, 3)
        )
        
        // Act
        val movie = movieDto.toDomainModel()
        
        // Assert
        assertThat(movie.id).isEqualTo(movieDto.id)
        assertThat(movie.title).isEqualTo(movieDto.title)
        assertThat(movie.overview).isEqualTo(movieDto.overview)
        assertThat(movie.posterPath).isEqualTo(movieDto.posterPath)
        assertThat(movie.backdropPath).isEqualTo(movieDto.backdropPath)
        assertThat(movie.voteAverage).isEqualTo(movieDto.voteAverage)
        assertThat(movie.genreIds).isEqualTo(movieDto.genreIds)
        
        // Verificar a data
        val expectedDate = LocalDate.parse("2023-01-15", DateTimeFormatter.ISO_DATE)
        assertThat(movie.releaseDate).isEqualTo(expectedDate)
    }
    
    @Test
    fun `MovieDto toDomainModel should handle null releaseDate`() {
        // Arrange
        val movieDto = MovieDto(
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
        val movie = movieDto.toDomainModel()
        
        // Assert
        assertThat(movie.releaseDate).isNull()
    }
    
    @Test
    fun `MovieDto toDomainModel should handle invalid releaseDate format`() {
        // Arrange
        val movieDto = MovieDto(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = "invalid-date", // Formato inválido
            genreIds = listOf(1, 2, 3)
        )
        
        // Act
        val movie = movieDto.toDomainModel()
        
        // Assert
        assertThat(movie.releaseDate).isNull()
    }
    
    @Test
    fun `MovieDetailsDto toDomainModel should correctly map all fields`() {
        // Arrange
        val movieDetailsDto = MovieDetailsDto(
            id = 1,
            title = "Test Movie Details",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = "2023-01-15",
            runtime = 120,
            genres = listOf(
                GenreDto(1, "Action"),
                GenreDto(2, "Adventure")
            ),
            voteCount = 1000,
            status = "Released",
            originalLanguage = "en"
        )
        
        // Act
        val movieDetails = movieDetailsDto.toDomainModel()
        
        // Assert
        assertThat(movieDetails.id).isEqualTo(movieDetailsDto.id)
        assertThat(movieDetails.title).isEqualTo(movieDetailsDto.title)
        assertThat(movieDetails.overview).isEqualTo(movieDetailsDto.overview)
        assertThat(movieDetails.posterPath).isEqualTo(movieDetailsDto.posterPath)
        assertThat(movieDetails.backdropPath).isEqualTo(movieDetailsDto.backdropPath)
        assertThat(movieDetails.voteAverage).isEqualTo(movieDetailsDto.voteAverage)
        assertThat(movieDetails.runtime).isEqualTo(movieDetailsDto.runtime)
        assertThat(movieDetails.genres).containsExactly("Action", "Adventure").inOrder()
        assertThat(movieDetails.voteCount).isEqualTo(movieDetailsDto.voteCount)
        assertThat(movieDetails.status).isEqualTo(movieDetailsDto.status)
        assertThat(movieDetails.originalLanguage).isEqualTo(movieDetailsDto.originalLanguage)
        
        // Verificar a data
        val expectedDate = LocalDate.parse("2023-01-15", DateTimeFormatter.ISO_DATE)
        assertThat(movieDetails.releaseDate).isEqualTo(expectedDate)
    }
} 