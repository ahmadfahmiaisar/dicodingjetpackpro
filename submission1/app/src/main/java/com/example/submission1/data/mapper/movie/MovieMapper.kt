package com.example.submission1.data.mapper.movie

import com.example.submission1.abstraction.Mapper
import com.example.submission1.data.response.movie.NowPlayingDto
import com.example.submission1.domain.entity.movie.MovieNowPlaying
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
}