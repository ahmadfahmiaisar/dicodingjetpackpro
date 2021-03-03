package com.example.submission.domain.repository

import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.entity.tvshow.TvShowEntity

interface TvShowRepository {
    suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>>
    suspend fun getTvShowDetail(idTv: Int): Result<TvShowDetail>

    suspend fun getAllTvFavorite(): Result<List<TvShowEntity>>
    suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int)
}