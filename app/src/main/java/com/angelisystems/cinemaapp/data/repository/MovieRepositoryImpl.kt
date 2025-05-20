package com.angelisystems.cinemaapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.angelisystems.cinemaapp.data.model.mapper.toDomainModel
import com.angelisystems.cinemaapp.data.source.remote.MovieApi
import com.angelisystems.cinemaapp.data.source.remote.MovieListType
import com.angelisystems.cinemaapp.data.source.remote.MoviePagingSource
import com.angelisystems.cinemaapp.data.source.remote.MovieSearchPagingSource
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.model.MovieDetails
import com.angelisystems.cinemaapp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

private const val TOTAL_PAGE_ITEMS = 10
private const val PREFETCH_DISTANCE = 3

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {
    
    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = movieApi.getPopularMovies(page = page)
            Result.success(response.results.map { it.toDomainModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
        return try {
            val response = movieApi.searchMovies(query = query, page = page)
            Result.success(response.results.map { it.toDomainModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
        return try {
            val response = movieApi.getMovieDetails(movieId = movieId)
            Result.success(response.toDomainModel())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = movieApi.getTopRatedMovies(page = page)
            Result.success(response.results.map { it.toDomainModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>> {
        return try {
            val response = movieApi.getUpcomingMovies(page = page)
            Result.success(response.results.map { it.toDomainModel() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getPopularMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = TOTAL_PAGE_ITEMS,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = TOTAL_PAGE_ITEMS
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, MovieListType.POPULAR)
            }
        ).flow
    }
    
    override fun getTopRatedMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = TOTAL_PAGE_ITEMS,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = TOTAL_PAGE_ITEMS
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, MovieListType.TOP_RATED)
            }
        ).flow
    }
    
    override fun getUpcomingMoviesPaging(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = TOTAL_PAGE_ITEMS,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = TOTAL_PAGE_ITEMS
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, MovieListType.UPCOMING)
            }
        ).flow
    }

    override fun searchMoviesPaging(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = TOTAL_PAGE_ITEMS,
                prefetchDistance = PREFETCH_DISTANCE,
                initialLoadSize = TOTAL_PAGE_ITEMS
            ),
            pagingSourceFactory = {
                MovieSearchPagingSource(movieApi, query)
            }
        ).flow
    }
} 