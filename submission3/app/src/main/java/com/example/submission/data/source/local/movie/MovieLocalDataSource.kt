package com.example.submission.data.source.local.movie

import com.example.submission.data.database.dao.MovieDao
import com.example.submission.data.database.entity.MovieEntity
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.domain.entity.movie.MovieNowPlaying
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(
    private val dao: MovieDao,
    private val movieMapper: MovieMapper
) : MovieLocalService {
    override suspend fun getMovie(dispatcher: CoroutineDispatcher): List<MovieNowPlaying> {
        return dao.getMovie().map { movieMapper.mapToDomain(it) }
    }

    override suspend fun insertMovie(
        dispatcher: CoroutineDispatcher,
        movies: List<MovieNowPlaying>
    ) {
        movies.map {
            dao.insert(movieMapper.toEntity(it))
        }
    }

    override suspend fun getAllMovieFavorite(dispatcher: CoroutineDispatcher): List<MovieNowPlaying> {
        return dao.getAllMovieFavorite().map { movieMapper.mapToDomain(it) }
    }

    override suspend fun updateFavoriteMovie(
        dispatcher: CoroutineDispatcher,
        isFavorite: Boolean, movieId: Int
    ) {
        dao.updateFavoriteMovie(isFavorite, movieId)
    }

}