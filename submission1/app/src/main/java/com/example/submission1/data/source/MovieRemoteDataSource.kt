package com.example.submission1.data.source

import com.example.submission1.data.response.NowPlayingDto
import com.example.submission1.data.response.TvOnTheAirDto
import com.example.submission1.data.services.MovieService
import com.example.submission1.data.vo.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieService: MovieService) :
    RemoteDataSource() {

    suspend fun getMovieNowPlaying(dispatcherProvider: CoroutineDispatcher): Result<NowPlayingDto> {
        return safeApiCall(dispatcherProvider) { movieService.getMovieNowPlaying() }
    }
}