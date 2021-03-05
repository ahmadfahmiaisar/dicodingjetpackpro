package com.example.submission.domain.repository

import androidx.paging.PagingData
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.entity.movie.MovieNowPlaying
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
//    suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>>
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetail>

    suspend fun getAllMovieFavorite(): Result<List<MovieEntity>>
    suspend fun updateFavoriteMovie(isFavorite: Boolean, movieId: Int)

    fun getAllMovie(): Flow<PagingData<MovieNowPlaying>>
}