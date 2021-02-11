package com.example.submission1.domain.usecase.movie

import com.example.submission1.abstraction.UseCase
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.movie.MovieNowPlaying
import com.example.submission1.domain.repository.MovieRepository
import com.example.submission1.util.IMAGE_BASE_URL_POSTER
import javax.inject.Inject

class GetMovieNowPlayingUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    UseCase<UseCase.None, Result<List<MovieNowPlaying>>>() {
    override suspend fun invoke(params: None): Result<List<MovieNowPlaying>> {
        return handleData(movieRepository.getMovieNowPlaying())
    }

    private fun handleData(result: Result<List<MovieNowPlaying>>): Result<List<MovieNowPlaying>> {
        return when (result) {
            is Result.Success -> Result.Success(result.data.map { it.toUiModel() })
            is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
            else -> Result.Error()
        }
    }

    private fun MovieNowPlaying.toUiModel(): MovieNowPlaying {
        return MovieNowPlaying(
            this.id,
            this.overview,
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}",
            this.title
        )
    }
}