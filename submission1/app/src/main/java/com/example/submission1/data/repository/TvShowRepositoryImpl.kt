package com.example.submission1.data.repository

import com.example.submission1.data.dispatcher.DispatcherProvider
import com.example.submission1.data.mapper.tvshow.TvOnTheAirMapper
import com.example.submission1.data.source.TvShowRemoteDataSource
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.tvshow.TvOnTheAir
import com.example.submission1.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvOnTheAirMapper: TvOnTheAirMapper
) : TvShowRepository {

    override suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>> {
        val apiResult = tvShowRemoteDataSource.getTvOnTheAir(dispatcher.io)
        return when (apiResult) {
            is Result.Success -> Result.Success(tvOnTheAirMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }
}