package com.example.submission1.data.services

import com.example.submission1.BuildConfig
import com.example.submission1.data.response.movie.MovieDetailDto
import com.example.submission1.data.response.movie.NowPlayingDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(@Query("api_key") apiKey: String = BuildConfig.API_KEY): NowPlayingDto

    @GET("movie/{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): MovieDetailDto
}