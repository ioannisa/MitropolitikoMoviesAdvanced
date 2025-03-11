package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.components.scaffold

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.components.AppBottomNavBar
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.components.AppTopAppBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ApplicationScaffold(
    scaffoldViewModel: ScaffoldViewModel = koinViewModel(),
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val title by scaffoldViewModel.title.collectAsState()
    val onBackPress by scaffoldViewModel.onBackPress.collectAsState()

    Scaffold(
        topBar = { if (title != null) AppTopAppBar(title, onBackPress) },
        bottomBar = { AppBottomNavBar(navController) }
    ) { paddingValues ->
        content(paddingValues)
    }
}