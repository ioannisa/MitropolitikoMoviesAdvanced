package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

/**
 * Allow for shared ViewModel within nested navigation using Koin
 */
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route
    return if (navGraphRoute != null) {
        val parentEntry = remember(this) {
            navController.getBackStackEntry(navGraphRoute)
        }
        koinViewModel(viewModelStoreOwner = parentEntry)
    } else {
        koinViewModel()
    }
}