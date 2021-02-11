package com.example.submission.data.repository

import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.tvshow.TvOnTheAirMapper
import com.example.submission.data.mapper.tvshow.TvShowDetailMapper
import com.example.submission.data.source.TvShowRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvOnTheAirMapper: TvOnTheAirMapper,
    private val tvShowDetailMapper: TvShowDetailMapper
) : TvShowRepository {

    override suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>> {
        val apiResult = tvShowRemoteDataSource.getTvOnTheAir(dispatcher.io)
        return when (apiResult) {
            is Result.Success -> Result.Success(tvOnTheAirMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override suspend fun getTvShowDetail(idTv: Int): Result<TvShowDetail> {
        val apiResult = tvShowRemoteDataSource.getTvShowDetail(dispatcher.io, idTv)
        return when (apiResult) {
            is Result.Success -> Result.Success(tvShowDetailMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }
}