package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import eu.anifantakis.mitropolitikomoviesadvanced.nav.NavGraph
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.Icons

data class BottomNavigationItem(
    val label : String = "",
    val icon : ImageVector? = null,
    val route : NavGraph = NavGraph.RandomMovies
) {

    //function to get the list of bottomNavigationItems
    @Composable
    fun bottomNavigationItems() : List<BottomNavigationItem> {
        return listOf(
            BottomNavigationItem(
                label = "Random",
                icon = Icons.favoriteOutlined,
                route = NavGraph.RandomMovies
            ),
            BottomNavigationItem(
                label = "Favorites",
                icon = Icons.favoriteFilled,
                route = NavGraph.FavoriteMovies
            ),
        )
    }
}

@Composable
fun AppBottomNavBar(navController: NavHostController) {

    var navigationSelectedItem by remember { mutableIntStateOf(0) }

    NavigationBar(tonalElevation = 4.dp) {
        BottomNavigationItem().bottomNavigationItems().forEachIndexed { index, navigationItem ->

            //iterating all items with their respective indexes
            NavigationBarItem(
                selected = index == navigationSelectedItem,
                label = {
                    Text(navigationItem.label)
                },
                icon = {
                    navigationItem.icon?.let { icon ->
                        Icon(imageVector = icon, contentDescription = navigationItem.label)
                    }
                },
                onClick = {

//                    navigationSelectedItem = index
//                    navController.popAndNavigate(
//                        popTo = navController.graph.findStartDestination().id,
//                        navigate = navigationItem.route
//                    )

                    navigationSelectedItem = index
                    navController.navigate(navigationItem.route) {

                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}