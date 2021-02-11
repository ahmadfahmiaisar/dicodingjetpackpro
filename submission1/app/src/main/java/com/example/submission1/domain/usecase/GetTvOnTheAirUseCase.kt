package com.example.submission1.domain.usecase

import com.example.submission1.abstraction.UseCase
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.TvOnTheAir
import com.example.submission1.domain.repository.MovieRepository
import com.example.submission1.util.IMAGE_BASE_URL_POSTER
import javax.inject.Inject

class GetTvOnTheAirUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    UseCase<UseCase.None, Result<List<TvOnTheAir>>>() {
    override suspend fun invoke(params: None): Result<List<TvOnTheAir>> {
        return handleData(movieRepository.getTvOnTheAir())
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
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}"
        )
    }
}