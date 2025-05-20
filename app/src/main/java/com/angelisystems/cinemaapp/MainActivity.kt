package com.angelisystems.cinemaapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.angelisystems.cinemaapp.presentation.navigation.AppNavigation
import com.angelisystems.cinemaapp.ui.theme.CinemaAppTheme
import com.angelisystems.cinemaapp.presentation.navigation.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CinemaAppContent()
        }
    }
}

@Composable
fun CinemaAppContent(
    useDarkTheme: Boolean = isSystemInDarkTheme()
) {
    CinemaAppTheme(darkTheme = useDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = currentRoute == Screen.Home.route,
                            onClick = { navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Home.route) { inclusive = true }
                                launchSingleTop = true
                            } },
                            icon = { Icon(Icons.Filled.Home, contentDescription = "Filmes") },
                            label = { Text("Filmes") }
                        )
                        NavigationBarItem(
                            selected = currentRoute == Screen.Favorites.route,
                            onClick = { navController.navigate(Screen.Favorites.route) {
                                popUpTo(Screen.Home.route)
                                launchSingleTop = true
                            } },
                            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favoritos") },
                            label = { Text("Favoritos") }
                        )
                    }
                }
            ) { innerPadding ->
                AppNavigation(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}