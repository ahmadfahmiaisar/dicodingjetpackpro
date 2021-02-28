package com.example.submission.data.mapper.movie

import com.example.submission.abstraction.Mapper
import com.example.submission.data.database.entity.MovieEntity
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
                it.title ?: ""
            )
        } ?: emptyList()
    }


    fun mapToDomain(input: MovieEntity): MovieNowPlaying {
        return MovieNowPlaying(
            input.movieId.toInt(),
            input.overview,
            input.posterPath,
            input.title
        )
    }

    fun toEntity(input: MovieNowPlaying): MovieEntity {
        return MovieEntity(
            input.id.toString(),
            input.title,
            input.posterPath,
            input.overview
        )
    }
}