package com.angelisystems.cinemaapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.repository.MovieRepository
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    // Fluxos de filmes com paginação
    val popularMoviesPaging = movieRepository.getPopularMoviesPaging().cachedIn(viewModelScope)
    val topRatedMoviesPaging = movieRepository.getTopRatedMoviesPaging().cachedIn(viewModelScope)
    val upcomingMoviesPaging = movieRepository.getUpcomingMoviesPaging().cachedIn(viewModelScope)

    // IDs dos filmes favoritos
    val favoriteIds: StateFlow<Set<Int>> = favoriteRepository.getAllFavoriteIds().map { it.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    fun onFavoriteClick(movie: Movie, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) favoriteRepository.removeFavorite(movie)
            else favoriteRepository.addFavorite(movie)
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upComingMovies: List<Movie> = emptyList(),
    val error: String? = null
) 