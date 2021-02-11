package com.example.submission.domain.repository

import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.entity.tvshow.TvShowDetail

interface TvShowRepository {
    suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>>
    suspend fun getTvShowDetail(idTv: Int): Result<TvShowDetail>
}