package com.angelisystems.cinemaapp.data.repository

import com.angelisystems.cinemaapp.data.source.local.FavoriteMovieDao
import com.angelisystems.cinemaapp.data.model.mapper.toDomainModel
import com.angelisystems.cinemaapp.data.model.mapper.toFavoriteEntity
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteMovieDao
) : FavoriteRepository {

    override fun getAllFavorites(): Flow<List<Movie>> = dao.getAll().map { list ->
        list.map { it.toDomainModel() }
    }

    override fun getAllFavoriteIds(): Flow<List<Int>> = dao.getAllIds()

    override suspend fun addFavorite(movie: Movie) = dao.insert(movie.toFavoriteEntity())

    override suspend fun removeFavorite(movie: Movie) = dao.delete(movie.toFavoriteEntity())

    override suspend fun isFavorite(movieId: Int): Boolean = dao.getById(movieId) != null
} 