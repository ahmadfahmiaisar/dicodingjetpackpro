package com.example.submission1.data.mapper.tvshow

import com.example.submission1.abstraction.Mapper
import com.example.submission1.data.response.tvshow.TvShowDetailDto
import com.example.submission1.domain.entity.tvshow.TvShowDetail
import javax.inject.Inject

class TvShowDetailMapper @Inject constructor() : Mapper<TvShowDetailDto, TvShowDetail>() {
    override fun map(input: TvShowDetailDto): TvShowDetail {
        return TvShowDetail(
            input.id ?: 0,
            input.name ?: "",
            input.overview ?: "",
            input.popularity ?: 0.0,
            input.posterPath ?: "",
            input.lastAirDate ?: "",
            input.voteAverage ?: 0.0,
            input.voteCount ?: 0
        )
    }
}