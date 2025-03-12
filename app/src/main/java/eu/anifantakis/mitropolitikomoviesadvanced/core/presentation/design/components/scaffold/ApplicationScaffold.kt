package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.components.AppBottomNavBar

@Composable
fun ApplicationScaffold(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = { AppBottomNavBar(navController) }
    ) { paddingValues ->
        content(paddingValues)
    }
}