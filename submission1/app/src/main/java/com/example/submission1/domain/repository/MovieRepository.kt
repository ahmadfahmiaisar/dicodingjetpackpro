package com.example.submission1.domain.repository

import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.NowPlaying
import com.example.submission1.domain.entity.TvOnTheAir

interface MovieRepository {
    suspend fun getMovieNowPlaying(): Result<List<NowPlaying>>
    suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>>
}