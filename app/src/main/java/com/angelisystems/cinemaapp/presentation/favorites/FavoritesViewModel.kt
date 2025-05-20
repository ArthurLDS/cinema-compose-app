package com.angelisystems.cinemaapp.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    val favorites: StateFlow<List<Movie>> = favoriteRepository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun addToFavorites(movie: Movie) {
        viewModelScope.launch {
            favoriteRepository.addFavorite(movie)
        }
    }

    fun removeFromFavorites(movie: Movie) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(movie)
        }
    }
} 