package com.example.submission.domain.usecase.tvshow

import com.example.submission.domain.repository.TvShowRepository
import javax.inject.Inject

class UpdateFavoriteTvUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(isFavorite: Boolean, tvId: Int) {
        return tvShowRepository.updateTvFavorite(isFavorite, tvId)
    }
}