package com.example.submission.domain.repository

import androidx.paging.PagingData
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.entity.tvshow.TvShowEntity
import kotlinx.coroutines.flow.Flow

interface TvShowRepository {
    suspend fun getTvOnTheAir(): Flow<PagingData<TvShowEntity>>
    suspend fun getTvShowDetail(idTv: Int): Result<TvShowDetail>

    fun getAllTvFavorite(): Flow<PagingData<TvShowEntity>>
    suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int)
}