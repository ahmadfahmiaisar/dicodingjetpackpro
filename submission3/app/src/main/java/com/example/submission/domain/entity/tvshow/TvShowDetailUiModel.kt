package com.example.submission.domain.entity.tvshow

data class TvShowDetailUiModel(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: String,
    val posterPath: String,
    val lastAirDate: String,
    val voteAverage: String,
    val voteCount: String
)