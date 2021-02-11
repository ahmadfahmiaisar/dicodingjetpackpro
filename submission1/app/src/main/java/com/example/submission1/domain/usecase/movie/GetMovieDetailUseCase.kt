package com.example.submission1.domain.usecase.movie

import com.example.submission1.abstraction.UseCase
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.movie.MovieDetail
import com.example.submission1.domain.repository.MovieRepository
import com.example.submission1.util.IMAGE_BASE_URL_POSTER
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository: MovieRepository) :
    UseCase<Int, Result<MovieDetail>>() {
    override suspend fun invoke(params: Int): Result<MovieDetail> {
        return handleData(repository.getMovieDetail(params))
    }

    private fun handleData(result: Result<MovieDetail>): Result<MovieDetail> {
        return when (result) {
            is Result.Success -> Result.Success(result.data.toUiModel())
            is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
            else -> Result.Error()
        }
    }

    private fun MovieDetail.toUiModel(): MovieDetail {
        return MovieDetail(
            this.id,
            this.overview,
            this.popularity,
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}",
            this.releaseDate,
            this.title,
            this.voteAverage,
            this.voteCount
        )
    }

}