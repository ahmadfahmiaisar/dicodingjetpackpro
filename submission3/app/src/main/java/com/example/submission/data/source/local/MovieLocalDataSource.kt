package com.example.submission.data.source.local

import com.example.submission.data.database.dao.MovieDao
import com.example.submission.data.database.entity.mapToDomain
import com.example.submission.data.database.entity.toEntity
import com.example.submission.domain.entity.movie.MovieNowPlaying
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieLocalDataSource @Inject constructor(private val dao: MovieDao) : MovieLocalService {
    override suspend fun getMovie(dispatcher: CoroutineDispatcher): List<MovieNowPlaying> {
        return dao.getMovie().map { it.mapToDomain() }
    }

    override suspend fun insertMovie(
        dispatcher: CoroutineDispatcher,
        movies: List<MovieNowPlaying>
    ) {
        movies.map {
            dao.insert(it.toEntity())
        }
    }
}