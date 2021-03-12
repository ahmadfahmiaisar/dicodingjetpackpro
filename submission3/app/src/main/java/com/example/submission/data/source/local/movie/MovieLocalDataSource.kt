package com.example.submission.data.source.local.movie

import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.example.submission.data.database.dao.MovieDao
import com.example.submission.domain.entity.movie.MovieEntity
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val dao: MovieDao
) : MovieLocalService {
    override fun getAllMovie(): DataSource.Factory<Int, MovieEntity> {
        return dao.getAllMovie()
    }

    override suspend fun getMovie(pageSize: Int): List<MovieEntity> {
        return dao.getMovie(pageSize)
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