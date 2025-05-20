package com.angelisystems.cinemaapp.presentation.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Search : Screen("search")
    data object Details : Screen("details/{movieId}") {
        fun createRoute(movieId: Int) = "details/$movieId"
    }
    data object Favorites : Screen("favorites")
} 