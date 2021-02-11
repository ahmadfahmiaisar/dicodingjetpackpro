package com.example.submission.domain.usecase.tvshow

import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowDetail
import com.example.submission.domain.entity.tvshow.TvShowDetailUiModel
import com.example.submission.domain.repository.TvShowRepository
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import javax.inject.Inject

class GetTvShowDetailUseCase @Inject constructor(private val repository: TvShowRepository) :
    UseCase<Int, Result<TvShowDetailUiModel>>() {
    override suspend fun invoke(params: Int): Result<TvShowDetailUiModel> {
        return handleData(repository.getTvShowDetail(params))
    }

    private fun handleData(result: Result<TvShowDetail>): Result<TvShowDetailUiModel> {
        return when (result) {
            is Result.Success -> Result.Success(result.data.toUiModel())
            is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
            else -> Result.Error()
        }
    }

    private fun TvShowDetail.toUiModel(): TvShowDetailUiModel {
        return TvShowDetailUiModel(
            this.id,
            this.name,
            "overview:\n${this.overview}",
            "popularity: ${this.popularity}",
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}",
            "last air date: ${this.lastAirDate}",
            "vote average: ${this.voteAverage}",
            "vote count: ${this.voteCount}"
        )
    }
}