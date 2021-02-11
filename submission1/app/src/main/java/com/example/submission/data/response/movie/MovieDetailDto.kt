package com.example.submission.data.response.movie

import com.squareup.moshi.Json

data class MovieDetailDto(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "overview")
    val overview: String?,
    @field:Json(name = "popularity")
    val popularity: Float?,
    @field:Json(name = "poster_path")
    val posterPath: String?,
    @field:Json(name = "release_date")
    val releaseDate: String?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "vote_average")
    val voteAverage: Float?,
    @field:Json(name = "vote_count")
    val voteCount: Int?
)