package eu.anifantakis.mitropolitikomoviesadvanced.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.components.scaffold.ApplicationScaffold
import kotlinx.serialization.Serializable

sealed interface NavGraph {
    @Serializable data object FavoriteMovies: NavGraph
    @Serializable data object RandomMovies: NavGraph
}

@Composable
fun NavigationRoot(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    ApplicationScaffold(navController = navController) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = NavGraph.RandomMovies,
            modifier = modifier
        ) {
            randomMoviesGraph(navController)
            favoriteMoviesGraph(navController)
        }
    }
}
