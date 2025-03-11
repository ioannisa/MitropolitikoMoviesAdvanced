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

sealed interface FavoriteMoviesNavType {
    @Serializable data object MoviesList: FavoriteMoviesNavType
    @Serializable data class SelectedMovie(val id: Int): FavoriteMoviesNavType
}

fun NavGraphBuilder.favoriteMoviesGraph(navController: NavHostController) {
    navigation<NavGraph.FavoriteMovies>(
        startDestination = FavoriteMoviesNavType.MoviesList,
    ) {
        composable<FavoriteMoviesNavType.MoviesList> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Favorite Movies List")
            }
        }
    }
}