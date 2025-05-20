package com.angelisystems.cinemaapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.angelisystems.cinemaapp.presentation.details.DetailsScreen
import com.angelisystems.cinemaapp.presentation.home.HomeScreen
import com.angelisystems.cinemaapp.presentation.search.SearchScreen
import com.angelisystems.cinemaapp.presentation.favorites.FavoritesScreen
import com.angelisystems.cinemaapp.presentation.favorites.FavoritesViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                },
                onSearchClick = {
                    navController.navigate(Screen.Search.route)
                }
            )
        }
        
        composable(Screen.Search.route) {
            SearchScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            DetailsScreen(
                movieId = movieId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Favorites.route) {
            val favoritesViewModel: FavoritesViewModel = hiltViewModel()
            // Recuperar os favoritos para passar para a tela
            val favoriteIds = favoritesViewModel.favorites.collectAsState().value.map { it.id }.toSet()
            FavoritesScreen(
                onMovieClick = { movieId ->
                    navController.navigate(Screen.Details.createRoute(movieId))
                },
                favoriteIds = favoriteIds,
                onFavoriteClick = { movie, isFavorite ->
                    if (isFavorite) favoritesViewModel.removeFromFavorites(movie)
                    else favoritesViewModel.addToFavorites(movie)
                }
            )
        }
    }
} 