package com.example.submission.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.tvshow.TvOnTheAirMapper
import com.example.submission.data.mapper.tvshow.TvShowDetailMapper
import com.example.submission.data.source.local.pagingsource.PagingTvShowFavoriteDataSource
import com.example.submission.data.source.local.remotemediator.TvShowRemoteMediator
import com.example.submission.data.source.local.tvshow.TvShowLocalDataSource
import com.example.submission.data.source.local.tvshow.remotekey.TvShowRemoteKeySource
import com.example.submission.data.source.remote.TvShowRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvShowRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvOnTheAirMapper: TvOnTheAirMapper,
    private val tvShowDetailMapper: TvShowDetailMapper,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowRemoteKeySource: TvShowRemoteKeySource
) : TvShowRepository {

    @ExperimentalPagingApi
    override suspend fun getTvOnTheAir(): Flow<PagingData<TvShowEntity>> {
        val pagingSourceFactory = { tvShowLocalDataSource.getTvShow() }
        val pagingConfig = PagingConfig(
            pageSize = 22,
            initialLoadSize = 22,
            enablePlaceholders = false
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = TvShowRemoteMediator(
                dispatcher,
                tvShowRemoteDataSource,
                tvShowLocalDataSource,
                tvShowRemoteKeySource,
                tvOnTheAirMapper
            )
        ).flow
    }

    override suspend fun getTvShowDetail(idTv: Int): Result<TvShowDetail> {
        val apiResult = tvShowRemoteDataSource.getTvShowDetail(dispatcher.io, idTv)
        return when (apiResult) {
            is Result.Success -> Result.Success(tvShowDetailMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override fun getAllTvFavorite(): Flow<PagingData<TvShowEntity>> {
        val pagingSourceFactory = { PagingTvShowFavoriteDataSource(tvShowLocalDataSource) }

        val pagingLocalConfig = PagingConfig(
            pageSize = 3,
            initialLoadSize = 3
        )
        return Pager(
            config = pagingLocalConfig,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { it }
    }

    override suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int) {
        return tvShowLocalDataSource.updateTvFavorite(isFavorite, tvId)
    }
}