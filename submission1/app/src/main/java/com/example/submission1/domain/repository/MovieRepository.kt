package com.example.submission1.domain.repository

import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.movie.MovieNowPlaying
import com.example.submission1.domain.entity.TvOnTheAir

interface MovieRepository {
    suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>>
    suspend fun getTvOnTheAir(): Result<List<TvOnTheAir>>
}