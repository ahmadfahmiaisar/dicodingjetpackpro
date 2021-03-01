package com.example.submission.data.mapper.movie

import com.example.submission.abstraction.Mapper
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.data.response.movie.NowPlayingDto
import com.example.submission.domain.entity.movie.MovieNowPlaying
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<NowPlayingDto, List<MovieNowPlaying>>() {
    override fun map(input: NowPlayingDto): List<MovieNowPlaying> {
        return input.results?.map {
            MovieNowPlaying(
                it.id ?: 0,
                it.overview ?: "",
                it.posterPath ?: "",
                it.title ?: "",
                false
            )
        } ?: emptyList()
    }


    fun mapToDomain(input: MovieEntity): MovieNowPlaying {
        return MovieNowPlaying(
            input.movieId.toInt(),
            input.overview,
            input.posterPath,
            input.title,
            input.isFavorite
        )
    }

    fun toEntity(input: NowPlayingDto): List<MovieEntity> {
        val movies = mutableListOf<MovieEntity>()
        input.results?.map {
            movies.add(
                MovieEntity(
                    it.id.toString() ?: "",
                    it.title ?: "",
                    it.posterPath ?: "",
                    it.overview ?: "",
                    false
                )
            )
        }
        return movies
    }
}