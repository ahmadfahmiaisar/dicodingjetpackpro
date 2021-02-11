package com.example.submission1.data.repository

import com.example.submission1.data.dispatcher.DispatcherProvider
import com.example.submission1.data.mapper.movie.MovieDetailMapper
import com.example.submission1.data.mapper.movie.MovieMapper
import com.example.submission1.data.source.MovieRemoteDataSource
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.movie.MovieDetail
import com.example.submission1.domain.entity.movie.MovieNowPlaying
import com.example.submission1.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val movieDetailMapper: MovieDetailMapper
) : MovieRepository {
    override suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>> {
        val apiResult = movieRemoteDataSource.getMovieNowPlaying(dispatcher.io)
        return when (apiResult) {
            is Result.Success -> Result.Success(movieMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
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