package com.example.submission.domain.usecase.tvshow

import androidx.paging.PagingData
import androidx.paging.map
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvOnTheAir
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.repository.TvShowRepository
import com.example.submission.util.IMAGE_BASE_URL_POSTER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTvOnTheAirUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) {
    suspend operator fun invoke(): Flow<PagingData<TvShowEntity>> {
        return tvShowRepository.getTvOnTheAir()
            .map {
                it.map { data -> handleData(data) }
            }
    }

    private fun handleData(data: TvShowEntity): TvShowEntity {
        return data.toUiModel()
    }

    private fun TvShowEntity.toUiModel(): TvShowEntity {
        return TvShowEntity(
            this.tvId,
            this.name,
            this.overview,
            "${IMAGE_BASE_URL_POSTER}${this.posterPath}",
            this.isFavorite
        )
    }
}