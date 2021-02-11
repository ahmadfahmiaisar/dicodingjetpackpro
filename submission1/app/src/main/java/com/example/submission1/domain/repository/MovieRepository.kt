package com.example.submission1.domain.repository

import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.movie.MovieDetail
import com.example.submission1.domain.entity.movie.MovieNowPlaying

interface MovieRepository {
    suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>
}