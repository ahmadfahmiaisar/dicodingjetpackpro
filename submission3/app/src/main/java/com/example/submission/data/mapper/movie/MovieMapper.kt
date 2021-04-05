package com.example.submission.data.mapper.movie

import com.example.submission.abstraction.Mapper
import com.example.submission.data.response.movie.NowPlayingDto
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.entity.movie.MovieNowPlaying
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<NowPlayingDto.Result, MovieNowPlaying>() {
    override fun map(input: NowPlayingDto.Result): MovieNowPlaying {
        return MovieNowPlaying(
            input.id ?: 0,
            input.overview ?: "",
            input.posterPath ?: "",
            input.title ?: "",
            false
        )
    }

    fun mapOld(input: NowPlayingDto): List<MovieNowPlaying> {
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

    fun mappingToDomain(input: MovieEntity): MovieNowPlaying {
        return MovieNowPlaying(
            input.movieId.toInt(),
            input.overview,
            input.posterPath,
            input.title,
            input.isFavorite
        )
    }

    fun mapToDomainList(input: List<MovieEntity>): List<MovieNowPlaying> {
        return input.map {
            MovieNowPlaying(
                it.movieId,
                it.overview,
                it.posterPath,
                it.title,
                it.isFavorite
            )
        }
    }

    fun toEntity(input: NowPlayingDto): List<MovieEntity> {
        val movies = mutableListOf<MovieEntity>()
        input.results.map {
            movies.add(
                MovieEntity(
                    it.id ?: 0,
                    it.title ?: "",
                    it.posterPath ?: "",
                    it.overview ?: "",
                    false
                )
            )
        }
        return movies
    }

    fun toEntityResult(input: NowPlayingDto.Result): List<MovieEntity> {
        val movies = mutableListOf<MovieEntity>()
        movies.add(
            MovieEntity(
                input.id ?: 0,
                input.title ?: "",
                input.posterPath ?: "",
                input.overview ?: "",
                false
            )
        )
        return movies
    }

    fun toEntityTok(input: NowPlayingDto.Result): MovieEntity {
        return MovieEntity(
            input.id ?: 0,
            input.title ?: "",
            input.posterPath ?: "",
            input.overview ?: "",
            false
        )
    }
}