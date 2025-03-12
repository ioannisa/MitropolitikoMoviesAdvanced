package eu.anifantakis.mitropolitikomoviesadvanced.movies.domain

import java.util.Date

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: Date,
    val voteAverage: Double,
    val posterPath: String,
    val backdropPath: String
)