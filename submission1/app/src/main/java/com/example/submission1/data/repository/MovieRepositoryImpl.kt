package com.example.submission1.data.repository

import com.example.submission1.data.dispatcher.DispatcherProvider
import com.example.submission1.data.mapper.MovieMapper
import com.example.submission1.data.mapper.TvOnTheAirMapper
import com.example.submission1.data.source.MovieRemoteDataSource
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.movie.MovieNowPlaying
import com.example.submission1.domain.entity.TvOnTheAir
import com.example.submission1.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val tvOnTheAirMapper: TvOnTheAirMapper
) : MovieRepository {
    override suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>> {
        val apiResult = remoteDataSource.getMovieNowPlaying(dispatcher.io)
        return when (apiResult) {
            is Result.Success -> Result.Success(movieMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>> {
        val apiResult = remoteDataSource.getTvOnTheAir(dispatcher.io)
        return when (apiResult) {
            is Result.Success -> Result.Success(tvOnTheAirMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }
}