package eu.anifantakis.mitropolitikomoviesadvanced.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.movies_list.MoviesListScreenRoot
import kotlinx.serialization.Serializable

sealed interface RandomMoviesRoute {
    @Serializable data object MoviesList: RandomMoviesRoute
    @Serializable data class SelectedMovie(val id: Int): RandomMoviesRoute
}

fun NavGraphBuilder.randomMoviesGraph(navController: NavHostController) {
    navigation<NavGraph.RandomMovies>(
        startDestination = RandomMoviesRoute.MoviesList,
    ) {
        composable<RandomMoviesRoute.MoviesList> {
            MoviesListScreenRoot(
                onNavigateToMovieDetails = { movieId ->
                    navController.navigate<RandomMoviesRoute.SelectedMovie>(
                        RandomMoviesRoute.SelectedMovie(movieId)
                    )
                }
            )
        }

        composable<RandomMoviesRoute.SelectedMovie> { entry ->
            val args = entry.toRoute<RandomMoviesRoute.SelectedMovie>()

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selected Movie: ${args.id}"
                )
            }
        }
    }
}