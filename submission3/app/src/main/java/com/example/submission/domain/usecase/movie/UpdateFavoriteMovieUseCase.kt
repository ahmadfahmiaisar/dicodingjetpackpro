package com.example.submission.domain.usecase.movie

import com.example.submission.domain.repository.MovieRepository
import javax.inject.Inject

class UpdateFavoriteMovieUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(isFavorite: Boolean, movieId: Int) {
        return movieRepository.updateFavoriteMovie(isFavorite, movieId)
    }

}