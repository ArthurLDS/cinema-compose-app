package com.angelisystems.cinemaapp.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.angelisystems.cinemaapp.domain.model.MovieDetails
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.repository.MovieRepository
import com.angelisystems.cinemaapp.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()
    
    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])
    
    // Estado de favorito
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()
    
    init {
        loadMovieDetails()
        observeFavorite()
    }
    
    fun loadMovieDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            movieRepository.getMovieDetails(movieId = movieId)
                .onSuccess { movieDetails ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            movieDetails = movieDetails,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Erro desconhecido ao carregar detalhes do filme"
                        )
                    }
                }
        }
    }
    
    private fun observeFavorite() {
        viewModelScope.launch {
            _uiState.collect { state ->
                val details = state.movieDetails
                if (details != null) {
                    _isFavorite.value = favoriteRepository.isFavorite(details.id)
                }
            }
        }
    }
    
    fun onFavoriteClick() {
        val details = uiState.value.movieDetails ?: return
        viewModelScope.launch {
            if (_isFavorite.value) {
                favoriteRepository.removeFavorite(details.toMovie())
                _isFavorite.value = false
            } else {
                favoriteRepository.addFavorite(details.toMovie())
                _isFavorite.value = true
            }
        }
    }
}

// Função de extensão para converter MovieDetails em Movie
fun MovieDetails.toMovie(): Movie = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    backdropPath = backdropPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate,
    genreIds = emptyList()
)

data class DetailsUiState(
    val isLoading: Boolean = false,
    val movieDetails: MovieDetails? = null,
    val error: String? = null
) 