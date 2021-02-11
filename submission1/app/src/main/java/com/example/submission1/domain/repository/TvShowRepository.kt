package com.example.submission1.domain.repository

import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.tvshow.TvOnTheAir
import com.example.submission1.domain.entity.tvshow.TvShowDetail

interface TvShowRepository {
    suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>>
    suspend fun getTvShowDetail(idTv: Int): Result<TvShowDetail>
}