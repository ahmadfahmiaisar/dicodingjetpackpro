package com.example.submission1.data.services

import com.example.submission1.BuildConfig
import com.example.submission1.data.response.tvshow.TvOnTheAirDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowService {
    @GET("tv/on_the_air")
    suspend fun getTvOnTheAir(@Query("api_key") apiKey: String = BuildConfig.API_KEY): TvOnTheAirDto

    @GET("tv/{tvId}")
    suspend fun getTvDetail(@Path("tvId") tvId: Int)
}