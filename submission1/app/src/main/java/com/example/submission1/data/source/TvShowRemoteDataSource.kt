package com.example.submission1.data.source

import com.example.submission1.data.response.tvshow.TvOnTheAirDto
import com.example.submission1.data.services.TvShowService
import com.example.submission1.data.vo.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TvShowRemoteDataSource @Inject constructor(private val tvShowService: TvShowService) :
    RemoteDataSource() {
    suspend fun getTvOnTheAir(dispatcherProvider: CoroutineDispatcher): Result<TvOnTheAirDto> {
        return safeApiCall(dispatcherProvider) { tvShowService.getTvOnTheAir() }
    }
}