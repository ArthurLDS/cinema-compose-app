package com.angelisystems.cinemaapp.domain.repository

import androidx.paging.PagingData
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getPopularMovies(page: Int): Result<List<Movie>>
    suspend fun searchMovies(query: String, page: Int): Result<List<Movie>>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>
    suspend fun getTopRatedMovies(page: Int): Result<List<Movie>>
    suspend fun getUpcomingMovies(page: Int): Result<List<Movie>>
    
    // Novos métodos usando Paging 3
    fun getPopularMoviesPaging(): Flow<PagingData<Movie>>
    fun getTopRatedMoviesPaging(): Flow<PagingData<Movie>>
    fun getUpcomingMoviesPaging(): Flow<PagingData<Movie>>

    fun searchMoviesPaging(query: String): Flow<PagingData<Movie>>
}