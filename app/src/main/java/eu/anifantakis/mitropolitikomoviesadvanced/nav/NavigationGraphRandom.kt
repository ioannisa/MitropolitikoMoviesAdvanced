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

sealed interface RandomMoviesNavType {
    @Serializable data object MoviesList: RandomMoviesNavType
    @Serializable data class SelectedMovie(val id: Int): RandomMoviesNavType
}

fun NavGraphBuilder.randomMoviesGraph(navController: NavHostController) {
    navigation<NavGraph.RandomMovies>(
        startDestination = RandomMoviesNavType.MoviesList,
    ) {
        composable<RandomMoviesNavType.MoviesList> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Random Movies List")
            }
        }
    }
}