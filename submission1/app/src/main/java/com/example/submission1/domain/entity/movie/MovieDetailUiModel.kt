package com.example.submission1.domain.entity.movie

data class MovieDetailUiModel(
    val id: Int,
    val overview: String,
    val popularity: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: String,
    val voteCount: String
)