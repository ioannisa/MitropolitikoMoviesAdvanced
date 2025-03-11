package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils

import androidx.navigation.NavHostController


/**
 * Pop to a specific destination and navigate to another
 */
inline fun <reified T : Any> NavHostController.popAndNavigate(popTo: T, navigate: T) {
    this.navigate(navigate) {
        popUpTo(popTo) {
            inclusive = true
            saveState = true
        }
        restoreState = true
    }
}