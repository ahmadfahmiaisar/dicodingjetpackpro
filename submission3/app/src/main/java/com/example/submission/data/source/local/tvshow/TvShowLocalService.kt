package com.example.submission.data.source.local.tvshow

import androidx.paging.PagingSource
import com.example.submission.domain.entity.tvshow.TvShowEntity

interface TvShowLocalService {
    fun getTvShow(): PagingSource<Int, TvShowEntity>
    suspend fun insertTvShow(tvShows: List<TvShowEntity>)

    suspend fun getAllTvFavorite(): List<TvShowEntity>
    suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int)
}