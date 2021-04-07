package com.example.submission.data.mapper.tvshow

import com.example.submission.abstraction.Mapper
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.data.response.tvshow.TvOnTheAirDto
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import javax.inject.Inject

class TvOnTheAirMapper @Inject constructor() : Mapper<TvOnTheAirDto, List<TvOnTheAir>>() {
    override fun map(input: TvOnTheAirDto): List<TvOnTheAir> {
        return input.results?.map {
            TvOnTheAir(
                it.id ?: 0,
                it.name ?: "",
                it.overview ?: "",
                it.posterPath ?: "",
                false
            )
        } ?: emptyList()
    }

    fun mapToDomain(input: List<TvShowEntity>): List<TvOnTheAir> {
        return input.map {
            TvOnTheAir(
                it.tvId,
                it.name,
                it.overview,
                it.posterPath,
                it.isFavorite
            )
        }
    }

    fun toEntity(input: TvOnTheAirDto): List<TvShowEntity> {
        val tvShows = mutableListOf<TvShowEntity>()
        input.results?.map {
            tvShows.add(
                TvShowEntity(
                    it.id ?: 0,
                    it.name ?: "",
                    it.posterPath ?: "",
                    it.overview ?: ""
                )
            )
        }
        return tvShows
    }
}