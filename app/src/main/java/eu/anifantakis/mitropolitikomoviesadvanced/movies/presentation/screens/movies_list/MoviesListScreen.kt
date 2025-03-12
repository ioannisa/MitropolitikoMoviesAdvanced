package eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.movies_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design.UIConst
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils.ObserveEffects
import eu.anifantakis.mitropolitikomoviesadvanced.movies.domain.Movie
import eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.components.RowItem
import eu.anifantakis.mitropolitikomoviesadvanced.ui.theme.MitropolitikoMoviesAdvancedTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@Composable
fun MoviesListScreenRoot(
    onNavigateToMovieDetails: (Movie) -> Unit,
    viewModel: MoviesListViewModel = koinViewModel()
) {
    ObserveEffects(viewModel.effects) { effect ->
        when (effect) {
            is MoviesListEffect.GotoMovieDetails -> {
                onNavigateToMovieDetails(effect.movie)
            }
            else -> { }
        }
    }

    Scaffold(
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            contentAlignment = Alignment.Center
        ) {
            MoviesListScreen(
                state = viewModel.state,
                onAction = viewModel::onAction
            )

            if (viewModel.state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun MoviesListScreen(
    state: MoviesListState,
    onAction: (MoviesListIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(UIConst.paddingNormal),
        verticalArrangement = Arrangement.spacedBy(UIConst.paddingSmall)
    ) {
        LazyColumn {
            items(
                items = state.movies,
                key = { it.id }
            ) { movie ->
                RowItem(
                    movie = movie,
                    modifier = Modifier
                        .clickable {
                            onAction(MoviesListIntent.SelectMovie(movie))
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MoviesListScreenPreview() {
    MitropolitikoMoviesAdvancedTheme {
        MoviesListScreen(
            state = MoviesListState(
                movies = listOf(
                    Movie(1, "Movie 1", "overview 1", Date(), 1.0, "/9Rj8l6gElLpRL7Kj17iZhrT5Zuw.jpg", "backdropPath 1"),
                    Movie(2, "Movie 2", "overview 2", Date(), 1.0, "/wigZBAmNrIhxp2FNGOROUAeHvdh.jpg", "backdropPath 1"),
                    Movie(3, "Movie 3", "overview 2", Date(), 1.0, "/bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg", "backdropPath 1"),
                )
            ),
            onAction = {}
        )
    }
}
