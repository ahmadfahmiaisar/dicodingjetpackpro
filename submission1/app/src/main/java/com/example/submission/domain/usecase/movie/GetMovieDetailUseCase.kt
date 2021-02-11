package com.example.submission.domain.usecase.movie

import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieDetailUiModel
import com.example.submission.domain.repository.MovieRepository
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(private val repository: MovieRepository) :
    UseCase<Int, Result<MovieDetailUiModel>>() {
    override suspend fun invoke(params: Int): Result<MovieDetailUiModel> {
        return handleData(repository.getMovieDetail(params))
    }

    private fun handleData(result: Result<MovieDetail>): Result<MovieDetailUiModel> {
        return when (result) {
            is Result.Success -> Result.Success(result.data.toUiModel())
            is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
            else -> Result.Error()
        }
    }

    private fun MovieDetail.toUiModel(): MovieDetailUiModel {
        return MovieDetailUiModel(
            this.id,
            "overview:\n${this.overview}",
            "popularity: ${this.popularity}",
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}",
            "release date: ${this.releaseDate}",
            this.title,
            "vote average: ${this.voteAverage}",
            "vote count: ${this.voteCount}"
        )
    }

}