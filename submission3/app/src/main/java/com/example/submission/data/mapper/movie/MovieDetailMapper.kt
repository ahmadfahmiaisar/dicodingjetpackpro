package com.example.submission.data.mapper.movie

import com.example.submission.abstraction.Mapper
import com.example.submission.data.response.movie.MovieDetailDto
import com.example.submission.domain.entity.movie.MovieDetail
import javax.inject.Inject

class MovieDetailMapper @Inject constructor() : Mapper<MovieDetailDto, MovieDetail>() {
    override fun map(input: MovieDetailDto): MovieDetail {
        return MovieDetail(
            input.id ?: 0,
            input.overview ?: "",
            input.popularity ?: 0f,
            input.posterPath ?: "",
            input.releaseDate ?: "",
            input.title ?: "",
            input.voteAverage ?: 0f,
            input.voteCount ?: 0
        )
    }
}