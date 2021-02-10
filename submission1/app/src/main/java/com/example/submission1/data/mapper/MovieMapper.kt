package com.example.submission1.data.mapper

import com.example.submission1.abstraction.Mapper
import com.example.submission1.data.response.NowPlayingDto
import com.example.submission1.domain.entity.NowPlaying
import javax.inject.Inject

class MovieMapper @Inject constructor() : Mapper<NowPlayingDto, List<NowPlaying>>() {
    override fun map(input: NowPlayingDto): List<NowPlaying> {
        return input.results?.map {
            NowPlaying(
                it.id ?: 0,
                it.overview ?: "",
                it.posterPath ?: "",
                it.title ?: ""
            )
        } ?: emptyList()
    }
}