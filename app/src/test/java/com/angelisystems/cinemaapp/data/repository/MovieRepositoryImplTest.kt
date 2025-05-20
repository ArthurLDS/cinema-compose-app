package com.angelisystems.cinemaapp.data.repository

import com.angelisystems.cinemaapp.data.model.dto.GenreDto
import com.angelisystems.cinemaapp.data.model.dto.MovieDetailsDto
import com.angelisystems.cinemaapp.data.model.dto.MovieDto
import com.angelisystems.cinemaapp.data.model.dto.MoviesResponseDto
import com.angelisystems.cinemaapp.data.source.remote.MovieApi
import com.angelisystems.cinemaapp.data.source.remote.MovieListType
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.model.MovieDetails
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private lateinit var movieApi: MovieApi
    private lateinit var repository: MovieRepositoryImpl
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        movieApi = mockk()
        repository = MovieRepositoryImpl(movieApi)
    }

    @Test
    fun `getPopularMovies should return success with list of movies when API call succeeds`() = runTest {
        // Arrange
        val movieResponse = createSampleMovieResponse()
        coEvery { movieApi.getPopularMovies(any()) } returns movieResponse

        // Act
        val result = repository.getPopularMovies(1)

        // Assert
        assertThat(result.isSuccess).isTrue()
        val movies = result.getOrNull()
        assertThat(movies).isNotNull()
        assertThat(movies?.size).isEqualTo(1)
        
        val movie = movies?.first()
        assertThat(movie?.id).isEqualTo(1)
        assertThat(movie?.title).isEqualTo("Test Movie")
    }

    @Test
    fun `getPopularMovies should return failure when API call fails`() = runTest {
        // Arrange
        coEvery { movieApi.getPopularMovies(any()) } throws IOException("Network error")

        // Act
        val result = repository.getPopularMovies(1)

        // Assert
        assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        assertThat(exception).isInstanceOf(IOException::class.java)
        assertThat(exception?.message).isEqualTo("Network error")
    }

    @Test
    fun `getTopRatedMovies should return success with list of movies when API call succeeds`() = runTest {
        // Arrange
        val movieResponse = createSampleMovieResponse()
        coEvery { movieApi.getTopRatedMovies(any()) } returns movieResponse

        // Act
        val result = repository.getTopRatedMovies(1)

        // Assert
        assertThat(result.isSuccess).isTrue()
        val movies = result.getOrNull()
        assertThat(movies).isNotNull()
        assertThat(movies?.size).isEqualTo(1)
    }

    @Test
    fun `getUpcomingMovies should return success with list of movies when API call succeeds`() = runTest {
        // Arrange
        val movieResponse = createSampleMovieResponse()
        coEvery { movieApi.getUpcomingMovies(any()) } returns movieResponse

        // Act
        val result = repository.getUpcomingMovies(1)

        // Assert
        assertThat(result.isSuccess).isTrue()
        val movies = result.getOrNull()
        assertThat(movies).isNotNull()
        assertThat(movies?.size).isEqualTo(1)
    }

    @Test
    fun `searchMovies should return success with list of movies when API call succeeds`() = runTest {
        // Arrange
        val movieResponse = createSampleMovieResponse()
        coEvery { movieApi.searchMovies(any(), any()) } returns movieResponse

        // Act
        val result = repository.searchMovies("test", 1)

        // Assert
        assertThat(result.isSuccess).isTrue()
        val movies = result.getOrNull()
        assertThat(movies).isNotNull()
        assertThat(movies?.size).isEqualTo(1)
    }

    @Test
    fun `getMovieDetails should return success with movie details when API call succeeds`() = runTest {
        // Arrange
        val movieDetailsDto = createSampleMovieDetailsDto()
        coEvery { movieApi.getMovieDetails(any()) } returns movieDetailsDto

        // Act
        val result = repository.getMovieDetails(1)

        // Assert
        assertThat(result.isSuccess).isTrue()
        val movieDetails = result.getOrNull()
        assertThat(movieDetails).isNotNull()
        assertThat(movieDetails?.id).isEqualTo(1)
        assertThat(movieDetails?.title).isEqualTo("Test Movie Details")
        assertThat(movieDetails?.genres).containsExactly("Action")
    }

    @Test
    fun `getMovieDetails should return failure when API call fails`() = runTest {
        // Arrange
        coEvery { movieApi.getMovieDetails(any()) } throws IOException("Network error")

        // Act
        val result = repository.getMovieDetails(1)

        // Assert
        assertThat(result.isFailure).isTrue()
        val exception = result.exceptionOrNull()
        assertThat(exception).isInstanceOf(IOException::class.java)
    }

    // Helper functions
    private fun createSampleMovieResponse(): MoviesResponseDto {
        return MoviesResponseDto(
            page = 1,
            results = listOf(
                MovieDto(
                    id = 1,
                    title = "Test Movie",
                    overview = "Test Overview",
                    posterPath = "/test.jpg",
                    backdropPath = "/backdrop.jpg",
                    voteAverage = 8.5,
                    releaseDate = "2023-01-01",
                    genreIds = listOf(1, 2)
                )
            ),
            totalPages = 10,
            totalResults = 100
        )
    }

    private fun createSampleMovieDetailsDto(): MovieDetailsDto {
        return MovieDetailsDto(
            id = 1,
            title = "Test Movie Details",
            overview = "Test Overview",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            voteAverage = 8.5,
            releaseDate = "2023-01-01",
            runtime = 120,
            genres = listOf(GenreDto(1, "Action")),
            voteCount = 100,
            status = "Released",
            originalLanguage = "en"
        )
    }
} 