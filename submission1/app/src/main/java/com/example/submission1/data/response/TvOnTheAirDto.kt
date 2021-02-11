package com.example.submission1.data.response

import com.squareup.moshi.Json

data class TvOnTheAirDto(
    @field:Json(name = "results")
    val results: List<Result>?
) {
    data class Result(
        @field:Json(name = "id")
        val id: Int?,
        @field:Json(name = "name")
        val name: String?,
        @field:Json(name = "overview")
        val overview: String?,
        @field:Json(name = "poster_path")
        val posterPath: String?
    )
}