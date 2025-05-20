package com.angelisystems.cinemaapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.angelisystems.cinemaapp.data.model.dto.FavoriteMovieDto

@Database(entities = [FavoriteMovieDto::class], version = 1, exportSchema = false)
abstract class CinemaDatabase : RoomDatabase() {
    abstract fun favoriteMovieDao(): FavoriteMovieDao
} 