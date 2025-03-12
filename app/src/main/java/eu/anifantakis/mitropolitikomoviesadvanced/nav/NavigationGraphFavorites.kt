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
import kotlinx.serialization.Serializable

sealed interface FavoriteMoviesRoute {
    @Serializable data object MoviesList: FavoriteMoviesRoute
    @Serializable data class SelectedMovie(val id: Int): FavoriteMoviesRoute
}

fun NavGraphBuilder.favoriteMoviesGraph(navController: NavHostController) {
    navigation<NavGraph.FavoriteMovies>(
        startDestination = FavoriteMoviesRoute.MoviesList,
    ) {
        composable<FavoriteMoviesRoute.MoviesList> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Favorite Movies List")
            }
        }
    }
}