package com.example.submission.domain.usecase.movie

import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.repository.MovieRepository
import javax.inject.Inject

class GetAllMovieFavoriteUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    UseCase<UseCase.None, Result<List<MovieNowPlaying>>>() {
    override suspend fun invoke(params: None): Result<List<MovieNowPlaying>> {
        return movieRepository.getAllMovieFavorite()
    }
}