package com.example.submission1.domain.entity.tvshow

data class TvShowDetail(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val lastAirDate: String,
    val voteAverage: Double,
    val voteCount: Int
)
