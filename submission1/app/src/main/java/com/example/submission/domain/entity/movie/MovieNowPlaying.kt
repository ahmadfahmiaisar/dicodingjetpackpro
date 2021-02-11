package com.example.submission.domain.entity.movie

data class MovieNowPlaying(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val title: String
)