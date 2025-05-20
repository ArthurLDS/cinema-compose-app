package com.angelisystems.cinemaapp.di

import com.angelisystems.cinemaapp.data.repository.MovieRepositoryImpl
import com.angelisystems.cinemaapp.domain.repository.MovieRepository
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import com.angelisystems.cinemaapp.data.repository.FavoriteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
} 