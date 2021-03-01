package com.example.submission.data.source.local.movie

import com.example.submission.domain.entity.movie.MovieEntity

interface MovieLocalService {
    suspend fun getMovie(): List<MovieEntity>
    suspend fun insertMovie(movies: List<MovieEntity>)

    suspend fun getAllMovieFavorite(): List<MovieEntity>
    suspend fun updateFavoriteMovie(
        isFavorite: Boolean,
        movieId: Int
    )
}