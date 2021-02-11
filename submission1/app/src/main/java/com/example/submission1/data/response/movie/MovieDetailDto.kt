package com.example.submission1.data.response.movie

import com.squareup.moshi.Json

data class MovieDetailDto(
    @field:Json(name = "id")
    val id: Int? = null,
    @field:Json(name = "overview")
    val overview: String? = null,
    @field:Json(name = "popularity")
    val popularity: Float? = null,
    @field:Json(name = "poster_path")
    val posterPath: String? = null,
    @field:Json(name = "release_date")
    val releaseDate: String? = null,
    @field:Json(name = "title")
    val title: String? = null,
    @field:Json(name = "vote_average")
    val voteAverage: Float? = null,
    @field:Json(name = "vote_count")
    val voteCount: Int? = null
)