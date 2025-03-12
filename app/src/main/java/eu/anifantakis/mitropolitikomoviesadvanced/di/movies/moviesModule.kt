package eu.anifantakis.mitropolitikomoviesadvanced.di.movies

import eu.anifantakis.mitropolitikomoviesadvanced.movies.presentation.screens.movies_list.MoviesListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val moviesModule = module {
    viewModelOf(::MoviesListViewModel)
}