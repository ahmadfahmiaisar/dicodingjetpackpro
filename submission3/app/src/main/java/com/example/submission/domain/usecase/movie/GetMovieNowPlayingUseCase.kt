package com.example.submission.domain.usecase.movie

import androidx.paging.PagingData
import androidx.paging.map
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.repository.MovieRepository
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieNowPlayingUseCase @Inject constructor(private val movieRepository: MovieRepository) {

    suspend operator fun invoke(): Flow<PagingData<MovieNowPlaying>> {
        return movieRepository.getAllMovie()
            .map {
                it.map { data -> handleData(data) }
            }
    }

    private fun handleData(result: MovieNowPlaying): MovieNowPlaying {
        return result.toUiModel()
    }

    private fun MovieNowPlaying.toUiModel(): MovieNowPlaying {
        return MovieNowPlaying(
            this.id,
            this.overview,
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}",
            this.title,
            this.isFavorite
        )
    }
}