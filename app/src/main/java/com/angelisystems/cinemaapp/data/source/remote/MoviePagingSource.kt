package com.angelisystems.cinemaapp.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.angelisystems.cinemaapp.data.model.mapper.toDomainModel
import com.angelisystems.cinemaapp.domain.model.Movie

enum class MovieListType {
    POPULAR, TOP_RATED, UPCOMING
}

class MoviePagingSource(
    private val movieApi: MovieApi,
    private val listType: MovieListType
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        
        return try {
            val response = when (listType) {
                MovieListType.POPULAR -> movieApi.getPopularMovies(page = page)
                MovieListType.TOP_RATED -> movieApi.getTopRatedMovies(page = page)
                MovieListType.UPCOMING -> movieApi.getUpcomingMovies(page = page)
            }
            
            val movies = response.results.map { it.toDomainModel() }
            val nextKey = if (response.page < response.totalPages) response.page + 1 else null
            val prevKey = if (response.page > 1) response.page - 1 else null
            
            LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
} 