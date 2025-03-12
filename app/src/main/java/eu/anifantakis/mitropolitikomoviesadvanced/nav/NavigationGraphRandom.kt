package eu.anifantakis.mitropolitikomoviesadvanced.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.movies_list.MoviesListScreenRoot
import kotlinx.serialization.Serializable

sealed interface RandomMoviesNavType {
    @Serializable data object MoviesList: RandomMoviesNavType
    @Serializable data class SelectedMovie(val id: Int): RandomMoviesNavType
}

fun NavGraphBuilder.randomMoviesGraph(navController: NavHostController) {
    navigation<NavGraph.RandomMovies>(
        startDestination = RandomMoviesNavType.MoviesList,
    ) {
        composable<RandomMoviesNavType.MoviesList> {
            MoviesListScreenRoot(
                onNavigateToMovieDetails = {}
            )
        }
    }
}