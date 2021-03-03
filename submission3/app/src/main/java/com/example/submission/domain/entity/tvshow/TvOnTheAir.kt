package com.example.submission.domain.entity.tvshow

data class TvOnTheAir(
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    var isFavorite: Boolean
)