package com.example.submission.data.source.local.movie

import com.example.submission.domain.entity.movie.MovieNowPlaying
import kotlinx.coroutines.CoroutineDispatcher

interface MovieLocalService {
    suspend fun getMovie(dispatcher: CoroutineDispatcher): List<MovieNowPlaying>
    suspend fun insertMovie(dispatcher: CoroutineDispatcher, movies: List<MovieNowPlaying>)

    suspend fun getAllMovieFavorite(dispatcher: CoroutineDispatcher): List<MovieNowPlaying>
}