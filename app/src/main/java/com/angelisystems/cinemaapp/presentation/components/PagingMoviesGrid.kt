package com.angelisystems.cinemaapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.angelisystems.cinemaapp.R
import com.angelisystems.cinemaapp.domain.model.Movie

@Composable
fun PagingMoviesGrid(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit,
    favoriteIds: Set<Int>,
    onFavoriteClick: (Movie, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    emptyMessage: String = stringResource(R.string.no_movies_found)
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        when {
            // Estado de carregamento inicial
            movies.loadState.refresh is LoadState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            // Estado de erro inicial
            movies.loadState.refresh is LoadState.Error -> {
                val error = (movies.loadState.refresh as LoadState.Error).error
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center)
                ) {
                    AppText(
                        textResId = R.string.error_generic,
                        size = TextSize.Medium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        formatArgs = arrayOf(error.message ?: stringResource(R.string.error_unknown))
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(onClick = { movies.retry() }) {
                        AppText(
                            textResId = R.string.retry,
                            size = TextSize.Medium
                        )
                    }
                }
            }
            
            // Lista vazia
            movies.itemCount == 0 -> {
                AppText(
                    text = emptyMessage,
                    size = TextSize.Large,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.Center)
                )
            }
            
            // Exibir conteúdo
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(movies.itemCount) { index ->
                        val movie = movies[index]
                        if (movie != null) {
                            MovieCard(
                                movie = movie,
                                onClick = { onMovieClick(movie.id) },
                                isFavorite = favoriteIds.contains(movie.id),
                                onFavoriteClick = { onFavoriteClick(movie, favoriteIds.contains(movie.id)) }
                            )
                        }
                    }
                    
                    // Carregamento de mais itens
                    if (movies.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    
                    // Erro ao carregar mais itens com botão de retry
                    if (movies.loadState.append is LoadState.Error) {
                        item {
                            val error = (movies.loadState.append as LoadState.Error).error
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                AppText(
                                    textResId = R.string.error_loading_more_movies,
                                    size = TextSize.Small,
                                    color = MaterialTheme.colorScheme.error,
                                    textAlign = TextAlign.Center
                                )
                                
                                Spacer(modifier = Modifier.height(8.dp))
                                
                                Button(onClick = { movies.retry() }) {
                                    AppText(
                                        textResId = R.string.retry,
                                        size = TextSize.Medium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
} 