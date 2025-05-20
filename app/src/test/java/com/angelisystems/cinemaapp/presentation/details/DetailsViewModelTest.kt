package com.angelisystems.cinemaapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.model.MovieDetails
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import com.angelisystems.cinemaapp.domain.repository.MovieRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var viewModel: DetailsViewModel
    private val testDispatcher = StandardTestDispatcher()
    
    private val movieId = 1

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)
        savedStateHandle = SavedStateHandle(mapOf("movieId" to movieId))
        
        // Configurar o mock para retornar sucesso por padrão
        coEvery { movieRepository.getMovieDetails(movieId) } returns Result.success(createSampleMovieDetails())
        coEvery { favoriteRepository.isFavorite(movieId) } returns false
        
        viewModel = DetailsViewModel(movieRepository, favoriteRepository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }


    @Test
    fun `loadMovieDetails should update state with movie details on success`() = runTest {
        // Arrange
        val sampleMovieDetails = createSampleMovieDetails()
        coEvery { movieRepository.getMovieDetails(movieId) } returns Result.success(sampleMovieDetails)
        
        // Act
        viewModel.loadMovieDetails()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Assert
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.movieDetails).isEqualTo(sampleMovieDetails)
            assertThat(state.error).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadMovieDetails should update state with error on failure`() = runTest {
        // Arrange
        val errorMessage = "Erro ao carregar detalhes"
        coEvery { movieRepository.getMovieDetails(movieId) } returns Result.failure(Exception(errorMessage))
        
        // Act
        viewModel.loadMovieDetails()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Assert
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.error).isEqualTo(errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onFavoriteClick should toggle favorite status`() = runTest {
        // Arrange - Setup favorite status
        coEvery { favoriteRepository.isFavorite(movieId) } returns false
        val sampleMovieDetails = createSampleMovieDetails()
        coEvery { movieRepository.getMovieDetails(movieId) } returns Result.success(sampleMovieDetails)
        
        // Force update of UI state to contain movie details
        viewModel.loadMovieDetails()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Act
        viewModel.onFavoriteClick()
        testDispatcher.scheduler.advanceUntilIdle()
        
        // Assert
        coVerify { favoriteRepository.addFavorite(any()) }
    }

    // Helper functions
    private fun createSampleMovieDetails(): MovieDetails {
        return MovieDetails(
            id = movieId,
            title = "Test Movie Details",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = LocalDate.of(2023, 1, 1),
            runtime = 120,
            genres = listOf("Action", "Adventure"),
            voteCount = 1000,
            status = "Released",
            originalLanguage = "en"
        )
    }
    
    private fun createSampleMovie(): Movie {
        return Movie(
            id = movieId,
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