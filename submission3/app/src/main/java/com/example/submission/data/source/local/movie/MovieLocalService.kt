package com.example.submission.data.source.local.movie

import androidx.paging.DataSource
import com.example.submission.domain.entity.movie.MovieEntity

interface MovieLocalService {
    fun getAllMovie(): DataSource.Factory<Int, MovieEntity>
    suspend fun getMovie(pageSize: Int): List<MovieEntity>
    suspend fun insertMovie(movies: List<MovieEntity>)

    suspend fun getAllMovieFavorite(): List<MovieEntity>
    suspend fun updateFavoriteMovie(
        isFavorite: Boolean,
        movieId: Int
    )
}