package com.example.submission.domain.usecase.tvshow

import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.repository.TvShowRepository
import javax.inject.Inject

class GetAllTvFavoriteUseCase @Inject constructor(private val tvShowRepository: TvShowRepository) :
    UseCase<UseCase.None, Result<List<TvShowEntity>>>() {
    override suspend fun invoke(params: None): Result<List<TvShowEntity>> {
        return tvShowRepository.getAllTvFavorite()
    }
}