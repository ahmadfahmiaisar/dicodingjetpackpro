package com.example.submission.domain.usecase.tvshow

import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.repository.TvShowRepository
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import javax.inject.Inject

class GetTvOnTheAirUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) :
    UseCase<UseCase.None, Result<List<TvOnTheAir>>>() {
    override suspend fun invoke(params: None): Result<List<TvOnTheAir>> {
        return handleData(tvShowRepository.getTvOnTheAir())
    }

    private fun handleData(result: Result<List<TvOnTheAir>>): Result<List<TvOnTheAir>> {
        return when (result) {
            is Result.Success -> Result.Success(result.data.map { it.toUiModel() })
            is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
            else -> Result.Error()
        }
    }

    private fun TvOnTheAir.toUiModel(): TvOnTheAir {
        return TvOnTheAir(
            this.id,
            this.name,
            this.overview,
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}",
            this.isFavorite
        )
    }
}