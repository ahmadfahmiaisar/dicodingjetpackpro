package com.example.submission.data.source.local.movie

import com.example.submission.data.database.dao.MovieDao
import com.example.submission.domain.entity.movie.MovieEntity
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val dao: MovieDao
) : MovieLocalService {
    override suspend fun getMovie(): List<MovieEntity> {
        return dao.getMovie()
    }

    override suspend fun insertMovie(
        movies: List<MovieEntity>
    ) {
        dao.insert(movies)
    }

    override suspend fun getAllMovieFavorite(): List<MovieEntity> {
        return dao.getAllMovieFavorite()
    }

    override suspend fun updateFavoriteMovie(
        isFavorite: Boolean, movieId: Int
    ) {
        dao.updateFavoriteMovie(isFavorite, movieId)
    }

}