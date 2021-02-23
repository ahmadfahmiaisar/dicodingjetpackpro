package com.example.submission.data.source.local

import com.example.submission.domain.entity.movie.MovieNowPlaying
import kotlinx.coroutines.CoroutineDispatcher

interface MovieLocalService {
    suspend fun getMovie(dispatcher: CoroutineDispatcher): List<MovieNowPlaying>
    suspend fun insertMovie(dispatcher: CoroutineDispatcher, movies: List<MovieNowPlaying>)
}