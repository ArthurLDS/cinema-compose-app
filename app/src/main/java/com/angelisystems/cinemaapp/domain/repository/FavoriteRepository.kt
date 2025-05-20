package com.angelisystems.cinemaapp.domain.repository

import com.angelisystems.cinemaapp.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun getAllFavorites(): Flow<List<Movie>>
    fun getAllFavoriteIds(): Flow<List<Int>>
    suspend fun addFavorite(movie: Movie)
    suspend fun removeFavorite(movie: Movie)
    suspend fun isFavorite(movieId: Int): Boolean
} 