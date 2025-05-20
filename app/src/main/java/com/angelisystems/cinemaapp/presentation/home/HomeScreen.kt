package com.angelisystems.cinemaapp.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.angelisystems.cinemaapp.R
import com.angelisystems.cinemaapp.presentation.components.AppText
import com.angelisystems.cinemaapp.presentation.components.ErrorMessage
import com.angelisystems.cinemaapp.presentation.components.LoadingIndicator
import com.angelisystems.cinemaapp.presentation.components.PagingMoviesRow
import com.angelisystems.cinemaapp.presentation.components.TextSize

// Tags para teste
const val HOME_SCREEN_TAG = "home_screen"
const val HOME_TITLE_TAG = "home_title"
const val SEARCH_BUTTON_TAG = "search_button"
const val LOADING_INDICATOR_TAG = "loading_indicator"
const val ERROR_MESSAGE_TAG = "error_message"
const val RETRY_BUTTON_TAG = "retry_button"
const val POPULAR_SECTION_TAG = "popular_section"
const val TOP_RATED_SECTION_TAG = "top_rated_section"
const val UPCOMING_SECTION_TAG = "upcoming_section"
const val POPULAR_MOVIES_LIST_TAG = "popular_movies_list"
const val TOP_RATED_MOVIES_LIST_TAG = "top_rated_movies_list"
const val UPCOMING_MOVIES_LIST_TAG = "upcoming_movies_list"

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onSearchClick: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    // Coletando LazyPagingItems para os fluxos de filmes paginados
    val popularMoviesPaging = viewModel.popularMoviesPaging.collectAsLazyPagingItems()
    val topRatedMoviesPaging = viewModel.topRatedMoviesPaging.collectAsLazyPagingItems()
    val upcomingMoviesPaging = viewModel.upcomingMoviesPaging.collectAsLazyPagingItems()

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = Modifier
            .testTag(HOME_SCREEN_TAG)
            .semantics {
                // Habilita o uso de testTag para UiAutomator
                testTagsAsResourceId = true
            },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    AppText(
                        textResId = R.string.home_title,
                        size = TextSize.LargeTitle,
                        modifier = Modifier.testTag(HOME_TITLE_TAG)
                    )
                },
                actions = {
                    IconButton(
                        onClick = onSearchClick,
                        modifier = Modifier.testTag(SEARCH_BUTTON_TAG)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.navigation_search)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> {
                    LoadingIndicator(
                        modifier = Modifier.testTag(LOADING_INDICATOR_TAG)
                    )
                }

                uiState.error != null -> {
                    ErrorMessage(
                        message = uiState.error ?: stringResource(R.string.error_unknown),
                        onRetry = {
                            popularMoviesPaging.retry()
                            topRatedMoviesPaging.retry()
                            upcomingMoviesPaging.retry()
                        },
                        modifier = Modifier
                            .align(Alignment.Center)
                            .testTag(ERROR_MESSAGE_TAG)
                    )
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                    ) {
                        // Seção de filmes populares com paginação
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .testTag(POPULAR_SECTION_TAG)
                        ) {
                            AppText(
                                textResId = R.string.section_popular,
                                size = TextSize.Headline,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            PagingMoviesRow(
                                movies = popularMoviesPaging,
                                onMovieClick = onMovieClick,
                                favoriteIds = favoriteIds,
                                onFavoriteClick = viewModel::onFavoriteClick,
                                modifier = Modifier.testTag(POPULAR_MOVIES_LIST_TAG)
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        // Seção de filmes mais bem avaliados com paginação
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .testTag(TOP_RATED_SECTION_TAG)
                        ) {
                            AppText(
                                textResId = R.string.section_top_rated,
                                size = TextSize.Headline,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            PagingMoviesRow(
                                movies = topRatedMoviesPaging,
                                onMovieClick = onMovieClick,
                                favoriteIds = favoriteIds,
                                onFavoriteClick = viewModel::onFavoriteClick,
                                modifier = Modifier.testTag(TOP_RATED_MOVIES_LIST_TAG)
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        // Seção de filmes disponíveis em breve com paginação
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .testTag(UPCOMING_SECTION_TAG)
                        ) {
                            AppText(
                                textResId = R.string.section_upcoming,
                                size = TextSize.Headline,
                                textAlign = TextAlign.Start,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                            Spacer(Modifier.height(8.dp))
                            PagingMoviesRow(
                                movies = upcomingMoviesPaging,
                                onMovieClick = onMovieClick,
                                favoriteIds = favoriteIds,
                                onFavoriteClick = viewModel::onFavoriteClick,
                                modifier = Modifier.testTag(UPCOMING_MOVIES_LIST_TAG)
                            )
                        }
                    }
                }
            }
        }
    }
}