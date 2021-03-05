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

    operator fun invoke(): Flow<PagingData<MovieNowPlaying>> {
        return movieRepository.getAllMovie()
            .map {
                it.map { data -> handleData(data) }
            }
    }


    /* override suspend fun invoke(params: None): Result<List<MovieNowPlaying>> {
         return handleData(movieRepository.getMovieNowPlaying())
     }*/

    private fun handleData(result: MovieNowPlaying): MovieNowPlaying {
        return result.toUiModel()
    }

    /* private fun handleData(result: Result<List<MovieNowPlaying>>): Result<List<MovieNowPlaying>> {
         return when (result) {
             is Result.Success -> Result.Success(result.data.map { it.toUiModel() })
             is Result.Error -> Result.Error(result.cause, result.code, result.errorMessage)
             else -> Result.Error()
         }
     }
 */
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