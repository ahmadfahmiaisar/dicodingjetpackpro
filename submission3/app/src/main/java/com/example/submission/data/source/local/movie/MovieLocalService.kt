package com.example.submission.data.source.local.movie

import androidx.paging.PagingSource
import com.example.submission.domain.entity.movie.MovieEntity

interface MovieLocalService {
    fun getMovie(): PagingSource<Int, MovieEntity>
    suspend fun insertMovie(movies: List<MovieEntity>)

    suspend fun getAllMovieFavorite(): List<MovieEntity>
    suspend fun updateFavoriteMovie(
        isFavorite: Boolean,
        movieId: Int
    )

    suspend fun clearMovie()
}