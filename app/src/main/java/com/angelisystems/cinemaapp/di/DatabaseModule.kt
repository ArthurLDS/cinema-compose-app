package com.angelisystems.cinemaapp.di

import android.content.Context
import androidx.room.Room
import com.angelisystems.cinemaapp.data.source.local.CinemaDatabase
import com.angelisystems.cinemaapp.data.source.local.FavoriteMovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CinemaDatabase =
        Room.databaseBuilder(
            context,
            CinemaDatabase::class.java,
            "cinema_db"
        ).build()

    @Provides
    fun provideFavoriteMovieDao(db: CinemaDatabase): FavoriteMovieDao = db.favoriteMovieDao()
} 