package com.example.submission.data.source.remote

import com.example.submission.data.response.tvshow.TvOnTheAirDto
import com.example.submission.data.response.tvshow.TvShowDetailDto
import com.example.submission.data.services.TvShowService
import com.example.submission.data.source.core.RemoteDataSource
import com.example.submission.data.vo.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TvShowRemoteDataSource @Inject constructor(private val tvShowService: TvShowService) :
    RemoteDataSource() {
    suspend fun getTvOnTheAir(dispatcherProvider: CoroutineDispatcher): Result<TvOnTheAirDto> {
        return safeApiCall(dispatcherProvider) { tvShowService.getTvOnTheAir() }
    }

    suspend fun getTvShowDetail(
        dispatcherProvider: CoroutineDispatcher,
        tvId: Int
    ): Result<TvShowDetailDto> {
        return safeApiCall(dispatcherProvider) { tvShowService.getTvShowDetail(tvId = tvId) }
    }
}