package com.example.modul5compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.modul5compose.MovieViewModel
import com.example.modul5compose.MovieViewModelFactory
import com.example.modul5compose.data.local.AppDatabase
import com.example.modul5compose.data.repository.MovieRepository
import com.example.modul5compose.ui.screen.DetailScreen
import com.example.modul5compose.ui.screen.HomeScreen
import com.example.modul5compose.ui.screen.LanguageScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { MovieRepository(database.movieDao()) }

    val movieViewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(repository)
    )

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController, viewModel = movieViewModel)
        }
        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            DetailScreen(
                navController = navController,
                movieId = movieId,
                viewModel = movieViewModel
            )
        }

        composable("language") { LanguageScreen(navController) }
    }
}
