package com.angelisystems.cinemaapp.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.time.LocalDate

class MovieTest {

    @Test
    fun `isRecommended should return true when voteAverage is 7 or higher`() {
        // Arrange
        val recommendedMovie = createMovie(voteAverage = 7.0)
        val highlyRecommendedMovie = createMovie(voteAverage = 9.5)
        
        // Act & Assert
        assertThat(recommendedMovie.isRecommended()).isTrue()
        assertThat(highlyRecommendedMovie.isRecommended()).isTrue()
    }
    
    @Test
    fun `isRecommended should return false when voteAverage is below 7`() {
        // Arrange
        val notRecommendedMovie = createMovie(voteAverage = 6.9)
        val lowRatedMovie = createMovie(voteAverage = 3.5)
        
        // Act & Assert
        assertThat(notRecommendedMovie.isRecommended()).isFalse()
        assertThat(lowRatedMovie.isRecommended()).isFalse()
    }
    
    @Test
    fun `ratingType should return RATING_HIGH when voteAverage is 7 or higher`() {
        // Arrange
        val highRatedMovie = createMovie(voteAverage = 7.0)
        val veryHighRatedMovie = createMovie(voteAverage = 9.0)
        
        // Act & Assert
        assertThat(highRatedMovie.ratingType()).isEqualTo(RatingType.RATING_HIGH)
        assertThat(veryHighRatedMovie.ratingType()).isEqualTo(RatingType.RATING_HIGH)
    }
    
    @Test
    fun `ratingType should return RATING_MEDIUM when voteAverage is between 5 and 7`() {
        // Arrange
        val mediumRatedMovie1 = createMovie(voteAverage = 5.0)
        val mediumRatedMovie2 = createMovie(voteAverage = 6.9)
        
        // Act & Assert
        assertThat(mediumRatedMovie1.ratingType()).isEqualTo(RatingType.RATING_MEDIUM)
        assertThat(mediumRatedMovie2.ratingType()).isEqualTo(RatingType.RATING_MEDIUM)
    }
    
    @Test
    fun `ratingType should return RATING_LOW when voteAverage is below 5`() {
        // Arrange
        val lowRatedMovie1 = createMovie(voteAverage = 4.9)
        val lowRatedMovie2 = createMovie(voteAverage = 1.0)
        
        // Act & Assert
        assertThat(lowRatedMovie1.ratingType()).isEqualTo(RatingType.RATING_LOW)
        assertThat(lowRatedMovie2.ratingType()).isEqualTo(RatingType.RATING_LOW)
    }
    
    // Helper Function
    private fun createMovie(voteAverage: Double): Movie {
        return Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = voteAverage,
            releaseDate = LocalDate.of(2023, 1, 1),
            genreIds = listOf(1, 2)
        )
    }
} 