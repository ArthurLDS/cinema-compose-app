package com.angelisystems.cinemaapp.data.repository

import app.cash.turbine.test
import com.angelisystems.cinemaapp.data.model.dto.FavoriteMovieDto
import com.angelisystems.cinemaapp.data.source.local.FavoriteMovieDao
import com.angelisystems.cinemaapp.domain.model.Movie
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class FavoriteRepositoryImplTest {

    private lateinit var dao: FavoriteMovieDao
    private lateinit var repository: FavoriteRepositoryImpl

    @Before
    fun setup() {
        dao = mockk(relaxed = true)
        repository = FavoriteRepositoryImpl(dao)
    }

    @Test
    fun `getAllFavorites should return mapped domain models`() = runTest {
        // Arrange
        val favoriteMovieEntities = listOf(
            FavoriteMovieDto(
                id = 1,
                title = "Test Movie",
                overview = "Test Overview",
                posterPath = "/test.jpg",
                backdropPath = "/backdrop.jpg",
                voteAverage = 8.5,
                releaseDate = "2023-01-01"
            )
        )
        every { dao.getAll() } returns flowOf(favoriteMovieEntities)

        // Act & Assert
        repository.getAllFavorites().test {
            val favorites = awaitItem()
            assertThat(favorites).hasSize(1)

            val movie = favorites.first()
            assertThat(movie.id).isEqualTo(1)
            assertThat(movie.title).isEqualTo("Test Movie")
            assertThat(movie.posterPath).isEqualTo("/test.jpg")
            // Fechando o test scope
            awaitComplete()
        }
    }

    @Test
    fun `getAllFavoriteIds should return list of movie ids`() = runTest {
        // Arrange
        val movieIds = listOf(1, 2, 3)
        every { dao.getAllIds() } returns flowOf(movieIds)

        // Act & Assert
        repository.getAllFavoriteIds().test {
            val ids = awaitItem()
            assertThat(ids).containsExactly(1, 2, 3)
            awaitComplete()
        }
    }

    @Test
    fun `addFavorite should call dao insert with correct entity`() = runTest {
        // Arrange
        val movie = createSampleMovie()
        
        // Act
        repository.addFavorite(movie)
        
        // Assert
        coVerify { dao.insert(any()) }
    }

    @Test
    fun `removeFavorite should call dao delete with correct entity`() = runTest {
        // Arrange
        val movie = createSampleMovie()
        
        // Act
        repository.removeFavorite(movie)
        
        // Assert
        coVerify { dao.delete(any()) }
    }

    @Test
    fun `isFavorite should return true when movie exists in database`() = runTest {
        // Arrange
        val movieId = 1
        val favoriteMovieEntity = FavoriteMovieDto(
            id = movieId,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = "2023-01-01"
        )
        coEvery { dao.getById(movieId) } returns favoriteMovieEntity

        // Act
        val result = repository.isFavorite(movieId)

        // Assert
        assertThat(result).isTrue()
    }

    @Test
    fun `isFavorite should return false when movie doesn't exist in database`() = runTest {
        // Arrange
        val movieId = 1
        coEvery { dao.getById(movieId) } returns null

        // Act
        val result = repository.isFavorite(movieId)

        // Assert
        assertThat(result).isFalse()
    }

    // Helper functions
    private fun createSampleMovie(): Movie {
        return Movie(
            id = 1,
            title = "Test Movie",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = LocalDate.of(2023, 1, 1),
            genreIds = listOf(1, 2)
        )
    }
} 