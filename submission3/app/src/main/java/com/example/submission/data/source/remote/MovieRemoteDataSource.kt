package com.example.submission.data.source.remote

import com.example.submission.data.response.movie.MovieDetailDto
import com.example.submission.data.response.movie.NowPlayingDto
import com.example.submission.data.services.MovieService
import com.example.submission.data.source.core.RemoteDataSource
import com.example.submission.data.vo.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(private val movieService: MovieService) :
    RemoteDataSource() {

    suspend fun getMovieNowPlaying(dispatcherProvider: CoroutineDispatcher): Result<NowPlayingDto> {
        return safeApiCall(dispatcherProvider) { movieService.getMovieNowPlaying() }
    }

    suspend fun getMovieDetail(
        dispatcherProvider: CoroutineDispatcher,
        movieId: Int
    ): Result<MovieDetailDto> {
        return safeApiCall(dispatcherProvider) { movieService.getMovieDetail(movieId = movieId) }
    }
}