package com.angelisystems.cinemaapp.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun PagingMoviesRow(
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Int) -> Unit,
    favoriteIds: Set<Int>,
    onFavoriteClick: (Movie, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        LazyHorizontalGrid(
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.height(300.dp),
            rows = GridCells.Fixed(1)
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
            
            // Item para carregamento adicional
            if (movies.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.size(120.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(32.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            
            // Item para erro de carregamento com botão de retry
            if (movies.loadState.append is LoadState.Error) {
                item {
                    Box(
                        modifier = Modifier.size(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AppText(
                                textResId = R.string.error_simple,
                                size = TextSize.Small,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(8.dp))
                            
                            Button(
                                onClick = { movies.retry() },
                                modifier = Modifier.padding(4.dp)
                            ) {
                                AppText(
                                    textResId = R.string.retry,
                                    size = TextSize.ExtraSmall
                                )
                            }
                        }
                    }
                }
            }
        }
        
        // Estado inicial de carregamento
        if (movies.loadState.refresh is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        
        // Estado inicial de erro com botão de retry
        if (movies.loadState.refresh is LoadState.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    AppText(
                        textResId = R.string.error_loading_movies,
                        size = TextSize.Medium,
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