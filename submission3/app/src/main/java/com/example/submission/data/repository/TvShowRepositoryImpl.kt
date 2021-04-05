package com.example.submission.data.repository

import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.tvshow.TvOnTheAirMapper
import com.example.submission.data.mapper.tvshow.TvShowDetailMapper
import com.example.submission.data.source.local.tvshow.TvShowLocalDataSource
import com.example.submission.data.source.remote.TvShowRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.repository.TvShowRepository
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvOnTheAirMapper: TvOnTheAirMapper,
    private val tvShowDetailMapper: TvShowDetailMapper,
    private val tvShowLocalDataSource: TvShowLocalDataSource
) : TvShowRepository {

    override suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>> {
        val apiResult = tvShowRemoteDataSource.getTvOnTheAir(dispatcher.io)
        val localResult = tvShowLocalDataSource.getTvShow()
        return when {
            !localResult.isNullOrEmpty() -> {
                Result.Success(tvOnTheAirMapper.mapToDomain(localResult))
            }
            else -> {
                when (apiResult) {
                    is Result.Success -> {
                        tvShowLocalDataSource.insertTvShow(tvOnTheAirMapper.toEntity(apiResult.data))
                        Result.Success(tvOnTheAirMapper.map(apiResult.data))
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

    override suspend fun getTvShowDetail(idTv: Int): Result<TvShowDetail> {
        val apiResult = tvShowRemoteDataSource.getTvShowDetail(dispatcher.io, idTv)
        return when (apiResult) {
            is Result.Success -> Result.Success(tvShowDetailMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override suspend fun getAllTvFavorite(): Result<List<TvShowEntity>> {
        val localResult = tvShowLocalDataSource.getAllTvFavorite()
        return when {
            localResult.isNullOrEmpty() -> Result.Error()
            else -> {
                Result.Success(localResult)
            }
        }
    }

    override suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int) {
        return tvShowLocalDataSource.updateTvFavorite(isFavorite, tvId)
    }
}