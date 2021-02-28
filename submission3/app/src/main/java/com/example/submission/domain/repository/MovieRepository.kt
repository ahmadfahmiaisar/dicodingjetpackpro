package com.example.submission.domain.repository

import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieNowPlaying

interface MovieRepository {
    suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>

    suspend fun getAllMovieFavorite(): Result<List<MovieNowPlaying>>
    suspend fun updateFavoriteMovie(isFavorite: Boolean, movieId: Int)
}