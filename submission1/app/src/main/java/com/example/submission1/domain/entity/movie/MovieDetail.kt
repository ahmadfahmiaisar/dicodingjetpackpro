package com.example.submission1.domain.entity.movie

data class MovieDetail(
    val id: Int,
    val overview: String,
    val popularity: Float,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Float,
    val voteCount: Int
)