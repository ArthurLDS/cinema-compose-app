package com.angelisystems.cinemaapp.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.angelisystems.cinemaapp.data.model.dto.FavoriteMovieDto
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: FavoriteMovieDto)

    @Delete
    suspend fun delete(movie: FavoriteMovieDto)

    @Query("SELECT * FROM favorite_movies")
    fun getAll(): Flow<List<FavoriteMovieDto>>

    @Query("SELECT id FROM favorite_movies")
    fun getAllIds(): Flow<List<Int>>

    @Query("SELECT * FROM favorite_movies WHERE id = :id")
    suspend fun getById(id: Int): FavoriteMovieDto?
} 