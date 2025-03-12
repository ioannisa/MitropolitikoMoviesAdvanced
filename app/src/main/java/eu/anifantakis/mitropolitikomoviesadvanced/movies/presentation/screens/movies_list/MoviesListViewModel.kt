package eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.movies_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils.toComposeState
import eu.anifantakis.mitropolitikomoviesadvanced.movies.domain.Movie
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import java.util.Date
import kotlin.Int

class MoviesListViewModel: ViewModel() {

    private val _state = MutableStateFlow(MoviesListState())
    val state by _state.toComposeState(viewModelScope)

    private val effectChannel = Channel<MoviesListEffect>()
    val effects = effectChannel.receiveAsFlow()

    init {
        loadMovies()
    }

    fun onAction(action: MoviesListIntent) {
        when (action) {
            is MoviesListIntent.LoadMovies -> loadMovies()
            is MoviesListIntent.SelectMovie -> selectMovie(action.movieId)
        }
    }

    private fun loadMovies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // fake network call
            delay(3000L)
            _state.update {
                it.copy(
                    movies = listOf(
                        Movie(1, "Movie 1", "overview 1", Date(), 1.0, "/9Rj8l6gElLpRL7Kj17iZhrT5Zuw.jpg", "backdropPath 1"),
                        Movie(2, "Movie 2", "overview 2", Date(), 1.0, "/wigZBAmNrIhxp2FNGOROUAeHvdh.jpg", "backdropPath 1"),
                        Movie(3, "Movie 3", "overview 2", Date(), 1.0, "/bvYjhsbxOBwpm8xLE5BhdA3a8CZ.jpg", "backdropPath 1"),
                    )
                )
            }

            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun selectMovie(movieId: Int) {
        val movie = state.movies.firstOrNull { it.id == movieId }
        _state.update { it.copy(selectedMovie = movie) }

        movie?.let { movie ->
            viewModelScope.launch {
                effectChannel.send(MoviesListEffect.GotoMovieDetails(movie.id))
            }
        }
    }
}