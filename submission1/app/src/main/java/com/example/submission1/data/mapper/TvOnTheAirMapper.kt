package com.example.submission1.data.mapper

import com.example.submission1.abstraction.Mapper
import com.example.submission1.data.response.TvOnTheAirDto
import com.example.submission1.domain.entity.TvOnTheAir
import javax.inject.Inject

class TvOnTheAirMapper @Inject constructor() : Mapper<TvOnTheAirDto, List<TvOnTheAir>>() {
    override fun map(input: TvOnTheAirDto): List<TvOnTheAir> {
        return input.results?.map {
            TvOnTheAir(
                it.id ?: 0,
                it.name ?: "",
                it.overview ?: "",
                it.posterPath ?: ""
            )
        } ?: emptyList()
    }
}