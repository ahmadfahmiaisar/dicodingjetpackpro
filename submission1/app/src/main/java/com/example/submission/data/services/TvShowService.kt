package com.example.submission.data.services

import com.example.submission.BuildConfig
import com.example.submission.data.response.tvshow.TvOnTheAirDto
import com.example.submission.data.response.tvshow.TvShowDetailDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET("tv/on_the_air")
    suspend fun getTvOnTheAir(@Query("api_key") apiKey: String = BuildConfig.API_KEY): TvOnTheAirDto

    @GET("tv/{tvId}")
    suspend fun getTvShowDetail(
        @Path("tvId") tvId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): TvShowDetailDto
}