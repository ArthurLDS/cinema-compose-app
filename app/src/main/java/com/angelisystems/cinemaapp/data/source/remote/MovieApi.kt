package com.angelisystems.cinemaapp.data.source.remote

import com.angelisystems.cinemaapp.data.model.dto.MovieDetailsDto
import com.angelisystems.cinemaapp.data.model.dto.MoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "pt-BR"
    ): MoviesResponseDto

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "pt-BR"
    ): MoviesResponseDto

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "pt-BR"
    ): MoviesResponseDto
    
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "pt-BR"
    ): MoviesResponseDto
    
    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "pt-BR"
    ): MovieDetailsDto
} 