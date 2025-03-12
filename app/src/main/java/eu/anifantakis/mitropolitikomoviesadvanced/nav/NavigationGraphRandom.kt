package eu.anifantakis.mitropolitikomoviesadvanced.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils.sharedViewModel
import eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.SelectedMovieViewModel
import eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.movies_list.MoviesListScreenRoot
import kotlinx.serialization.Serializable
import androidx.compose.runtime.getValue

sealed interface RandomMoviesRoute {
    @Serializable data object MoviesList: RandomMoviesRoute
    @Serializable data class SelectedMovie(val id: Int): RandomMoviesRoute
}

fun NavGraphBuilder.randomMoviesGraph(navController: NavHostController) {
    navigation<NavGraph.RandomMovies>(
        startDestination = RandomMoviesRoute.MoviesList,
    ) {
        composable<RandomMoviesRoute.MoviesList> { entry ->
            val sharedViewModel = entry.sharedViewModel<SelectedMovieViewModel>(navController)

            // when we enter, or return to main screen, clear the selected movie
            LaunchedEffect(true) {
                sharedViewModel.onSelectMovie(null)
            }

            MoviesListScreenRoot(
                onNavigateToMovieDetails = { movie ->
                    // update the shared view model
                    sharedViewModel.onSelectMovie(movie)

                    // navigate to detail screen
                    navController.navigate<RandomMoviesRoute.SelectedMovie>(
                        RandomMoviesRoute.SelectedMovie(movie.id)
                    )
                }
            )
        }

        composable<RandomMoviesRoute.SelectedMovie> { entry ->
            val args = entry.toRoute<RandomMoviesRoute.SelectedMovie>()

            val sharedViewModel = entry.sharedViewModel<SelectedMovieViewModel>(navController)
            val selectedMovie by sharedViewModel.selectedMovie.collectAsStateWithLifecycle()

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selected Movie: ${args.id} \n\n  ${selectedMovie.toString()}"
                )
            }
        }
    }
}