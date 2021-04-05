package com.example.submission.data.source.local.tvshow

import com.example.submission.domain.entity.tvshow.TvShowEntity
import kotlinx.coroutines.CoroutineDispatcher

interface TvShowLocalService {
   suspend fun getTvShow(): List<TvShowEntity>
    suspend fun insertTvShow(tvShows: List<TvShowEntity>)

    suspend fun getAllTvFavorite(): List<TvShowEntity>
    suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int)
}