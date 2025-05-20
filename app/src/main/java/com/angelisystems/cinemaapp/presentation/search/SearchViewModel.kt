package com.angelisystems.cinemaapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(SearchState())
    val uiState: StateFlow<SearchState> = _uiState.asStateFlow()
    
    private val _searchMoviesPaging = MutableStateFlow<Flow<PagingData<Movie>>>(emptyFlow())
    val searchMoviesPaging: StateFlow<Flow<PagingData<Movie>>> = _searchMoviesPaging
    
    private var searchJob: Job? = null
    
    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        
        // Cancela pesquisas anteriores para evitar resultados fora de ordem
        searchJob?.cancel()
        
        if (query.isBlank()) {
            _uiState.update { it.copy(movies = emptyList()) }
            _searchMoviesPaging.value = emptyFlow()
            return
        }
        
        // Adiciona um pequeno atraso para evitar consultas a cada caractere digitado
        searchJob = viewModelScope.launch {
            delay(400) // debounce de 400ms
            
            // Busca com paginação
            _searchMoviesPaging.value = movieRepository.searchMoviesPaging(query)
                .cachedIn(viewModelScope)
            
            // Mantém a busca tradicional para compatibilidade com a UI atual
            searchMovies(query)
        }
    }
    
    private fun searchMovies(query: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            movieRepository.searchMovies(query = query, page = 1)
                .onSuccess { movies ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            movies = movies,
                            error = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Erro desconhecido ao buscar filmes"
                        )
                    }
                }
        }
    }
}

data class SearchState(
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null
) 