package com.example.submission.domain.usecase.tvshow

import androidx.paging.PagingData
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.repository.TvShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTvFavoriteUseCase @Inject constructor(private val tvShowRepository: TvShowRepository)  {
     operator fun invoke(): Flow<PagingData<TvShowEntity>> {
        return tvShowRepository.getAllTvFavorite()
    }
}