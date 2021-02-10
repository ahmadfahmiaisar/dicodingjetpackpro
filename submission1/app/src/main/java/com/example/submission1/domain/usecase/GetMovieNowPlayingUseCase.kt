package com.example.submission1.domain.usecase

import com.example.submission1.abstraction.UseCase
import com.example.submission1.data.vo.Result
import com.example.submission1.domain.entity.NowPlaying
import com.example.submission1.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieNowPlayingUseCase @Inject constructor(private val movieRepository: MovieRepository) :
    UseCase<UseCase.None, Result<List<NowPlaying>>>() {
    override suspend fun invoke(params: None): Result<List<NowPlaying>> {
        return movieRepository.getMovieNowPlaying()
    }
}