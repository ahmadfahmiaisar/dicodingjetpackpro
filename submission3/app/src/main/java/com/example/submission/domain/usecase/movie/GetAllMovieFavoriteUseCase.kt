package com.example.submission.domain.usecase.movie

import androidx.paging.PagingData
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllMovieFavoriteUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(): Flow<PagingData<MovieEntity>> {
        return movieRepository.getAllMovieFavorite()
    }
}