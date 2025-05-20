package com.angelisystems.cinemaapp.presentation.favorites

import app.cash.turbine.test
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class FavoritesViewModelTest {

    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var viewModel: FavoritesViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        favoriteRepository = mockk(relaxed = true)
        
        // Configurar o mock para retornar uma lista vazia por padrão
        every { favoriteRepository.getAllFavorites() } returns flowOf(emptyList())
        
        viewModel = FavoritesViewModel(favoriteRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `favorites should emit list from repository`() = runTest {
        // Arrange
        val sampleMovies = listOf(createSampleMovie(1), createSampleMovie(2))
        every { favoriteRepository.getAllFavorites() } returns flowOf(sampleMovies)
        
        // Recriando o viewModel para que ele utilize o flow mockado
        viewModel = FavoritesViewModel(favoriteRepository)
        
        // Act & Assert
        viewModel.favorites.test {
            awaitItem()
            val favorites = awaitItem()
            assertThat(favorites).hasSize(2)
            assertThat(favorites[0].id).isEqualTo(1)
            assertThat(favorites[1].id).isEqualTo(2)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addToFavorites should call repository addFavorite`() = runTest {
        // Arrange
        val movie = createSampleMovie(1)
        
        // Act
        viewModel.addToFavorites(movie)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Assert
        coVerify { favoriteRepository.addFavorite(movie) }
    }

    @Test
    fun `removeFromFavorites should call repository removeFavorite`() = runTest {
        // Arrange
        val movie = createSampleMovie(1)
        
        // Act
        viewModel.removeFromFavorites(movie)
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Assert
        coVerify { favoriteRepository.removeFavorite(movie) }
    }

    // Helper functions
    private fun createSampleMovie(id: Int): Movie {
        return Movie(
            id = id,
            title = "Test Movie $id",
            overview = "Test Overview",
            posterPath = "/test$id.jpg",
            backdropPath = "/backdrop$id.jpg",
            voteAverage = 8.5,
            releaseDate = LocalDate.of(2023, 1, 1),
            genreIds = listOf(1, 2)
        )
    }
} 