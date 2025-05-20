package com.angelisystems.cinemaapp.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.angelisystems.cinemaapp.R
import com.angelisystems.cinemaapp.domain.model.Movie
import com.angelisystems.cinemaapp.presentation.components.AppText
import com.angelisystems.cinemaapp.presentation.components.ErrorMessage
import com.angelisystems.cinemaapp.presentation.components.LoadingIndicator
import com.angelisystems.cinemaapp.presentation.components.MovieCard
import com.angelisystems.cinemaapp.presentation.components.PagingMoviesGrid
import com.angelisystems.cinemaapp.presentation.components.TextSize
import com.angelisystems.cinemaapp.presentation.favorites.FavoritesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onMovieClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchPagingFlow by viewModel.searchMoviesPaging.collectAsState()
    val searchPagingItems = searchPagingFlow.collectAsLazyPagingItems()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    AppText(
                        textResId = R.string.search_title,
                        size = TextSize.LargeTitle
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de pesquisa
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onSearch = {},
                active = true,
                onActiveChange = {},
                placeholder = { 
                    AppText(
                        textResId = R.string.search_hint,
                        size = TextSize.Medium
                    ) 
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = stringResource(R.string.navigation_search)
                    )
                },
                trailingIcon = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = stringResource(R.string.navigation_clear)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                // Conteúdo da SearchBar expandida
                when {
                    uiState.searchQuery.isBlank() -> {
                        AppText(
                            textResId = R.string.search_empty_hint,
                            size = TextSize.Large,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                    
                    else -> {
                        PagingMoviesGrid(
                            movies = searchPagingItems,
                            onMovieClick = onMovieClick,
                            favoriteIds = emptySet(), // Não tem favoritos na tela de busca
                            onFavoriteClick = { _, _ -> }, // Não permite favoritar na busca
                            emptyMessage = stringResource(R.string.search_no_results, uiState.searchQuery)
                        )
                    }
                }
            }
        }
    }
} 