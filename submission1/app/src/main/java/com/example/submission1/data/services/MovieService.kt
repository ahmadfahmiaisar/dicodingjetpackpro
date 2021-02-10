package com.example.submission1.data.services

import com.example.submission1.BuildConfig
import com.example.submission1.data.response.NowPlayingDto
import com.example.submission1.data.response.TvOnTheAirDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(@Query("api_key") apiKey: String = BuildConfig.API_KEY): NowPlayingDto

    @GET("tv/on_the_air")
    suspend fun getTvOnTheAir(@Query("api_key") apiKey: String = BuildConfig.API_KEY): TvOnTheAirDto
}