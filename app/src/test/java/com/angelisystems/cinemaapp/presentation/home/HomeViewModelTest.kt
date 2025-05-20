package com.angelisystems.cinemaapp.presentation.home

import androidx.paging.PagingData
import app.cash.turbine.test
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import com.angelisystems.cinemaapp.domain.repository.MovieRepository
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
class HomeViewModelTest {

    private lateinit var movieRepository: MovieRepository
    private lateinit var favoriteRepository: FavoriteRepository
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieRepository = mockk(relaxed = true)
        favoriteRepository = mockk(relaxed = true)

        // Mock dos métodos paginados retornando PagingData vazio
        every { movieRepository.getPopularMoviesPaging() } returns flowOf(PagingData.empty())
        every { movieRepository.getTopRatedMoviesPaging() } returns flowOf(PagingData.empty())
        every { movieRepository.getUpcomingMoviesPaging() } returns flowOf(PagingData.empty())
        
        // Mock dos favoritos
        every { favoriteRepository.getAllFavoriteIds() } returns flowOf(listOf(1, 2))

        viewModel = HomeViewModel(movieRepository, favoriteRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState should initialize with empty movies and isLoading false`() = runTest {
        // Act & Assert
        viewModel.uiState.test {
            val state = awaitItem()
            assertThat(state.isLoading).isFalse()
            assertThat(state.popularMovies).isEmpty()
            assertThat(state.topRatedMovies).isEmpty()
            assertThat(state.upComingMovies).isEmpty()
            assertThat(state.error).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onFavoriteClick should add movie to favorites when not favorite`() = runTest {
        // Arrange
        val movie = createSampleMovie()
        val isFavorite = false

        // Act
        viewModel.onFavoriteClick(movie, isFavorite)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { favoriteRepository.addFavorite(movie) }
    }

    @Test
    fun `onFavoriteClick should remove movie from favorites when already favorite`() = runTest {
        // Arrange
        val movie = createSampleMovie()
        val isFavorite = true

        // Act
        viewModel.onFavoriteClick(movie, isFavorite)
        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        coVerify { favoriteRepository.removeFavorite(movie) }
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