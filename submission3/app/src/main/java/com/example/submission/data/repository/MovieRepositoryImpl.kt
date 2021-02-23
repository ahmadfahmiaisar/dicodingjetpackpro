package com.example.submission.data.repository

import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.movie.MovieDetailMapper
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.data.source.local.MovieLocalDataSource
import com.example.submission.data.source.remote.MovieRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
    override suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>> {
        val apiResult = movieRemoteDataSource.getMovieNowPlaying(dispatcher.io)
        val localResult = movieLocalDataSource.getMovie(dispatcher.io)
        val movie = mutableListOf<MovieNowPlaying>()
        localResult.forEach {
            movie.add(it)
        }
        Timber.tag("ISINYAAA").d("$movie dan $localResult")
        return when {
            !localResult.isNullOrEmpty() -> {
                Timber.tag("ISINYA LOCAL")
                Result.Success(movie)
            }
            else -> {
                when (apiResult) {
                    is Result.Success -> {
                        Timber.tag("ISINYA REMOTE")
                        movieLocalDataSource.insertMovie(
                            dispatcher.io,
                            movieMapper.map(apiResult.data)
                        )
                        Result.Success(movieMapper.map(apiResult.data))
                    }
                    is Result.Error -> Result.Error(
                        apiResult.cause,
                        apiResult.code,
                        apiResult.errorMessage
                    )
                    else -> Result.Error()
                }
            }
        }

    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        val apiResult = movieRemoteDataSource.getMovieDetail(dispatcher.io, movieId)
        return when (apiResult) {
            is Result.Success -> Result.Success(movieDetailMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }
}