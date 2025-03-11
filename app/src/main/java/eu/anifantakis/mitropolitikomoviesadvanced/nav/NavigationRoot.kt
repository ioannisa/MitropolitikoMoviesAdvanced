package eu.anifantakis.mitropolitikomoviesadvanced.nav

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
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
    innerPadding: PaddingValues,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    ApplicationScaffold(navController = navController) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = NavGraph.RandomMovies,
            modifier = modifier.padding(
                PaddingValues(
                    top = maxOf(innerPadding.calculateTopPadding(), scaffoldPadding.calculateTopPadding()),
                    bottom = maxOf(innerPadding.calculateBottomPadding(), scaffoldPadding.calculateBottomPadding()),
                    start = maxOf(innerPadding.calculateStartPadding(LayoutDirection.Ltr), scaffoldPadding.calculateStartPadding(LayoutDirection.Ltr)),
                    end = maxOf(innerPadding.calculateEndPadding(LayoutDirection.Ltr), scaffoldPadding.calculateEndPadding(LayoutDirection.Ltr))
                )
            )
        ) {
            randomMoviesGraph(navController)
            favoriteMoviesGraph(navController)
        }
    }
}
