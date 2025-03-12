package eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.movies_list

import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils.UiText
import eu.anifantakis.mitropolitikomoviesadvanced.movies.domain.Movie

sealed interface MoviesListIntent {
    data object LoadMovies : MoviesListIntent
    data class SelectMovie(val movie: Movie) : MoviesListIntent
}

sealed interface MoviesListEffect {
    data object MoviesListSuccess : MoviesListEffect
    data class Error(val error: UiText) : MoviesListEffect
    data class GotoMovieDetails(val movie: Movie) : MoviesListEffect
}

data class MoviesListState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
)