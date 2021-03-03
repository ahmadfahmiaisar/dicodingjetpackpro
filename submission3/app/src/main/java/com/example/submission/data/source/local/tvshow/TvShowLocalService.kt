package com.example.submission.data.source.local.tvshow

import androidx.lifecycle.LiveData
import com.example.submission.domain.entity.tvshow.TvShowEntity

interface TvShowLocalService {
    suspend fun getTvShow(): LiveData<List<TvShowEntity>>
    suspend fun insertTvShow(tvShows: List<TvShowEntity>)

    suspend fun getAllTvFavorite(): List<TvShowEntity>
    suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int)
}