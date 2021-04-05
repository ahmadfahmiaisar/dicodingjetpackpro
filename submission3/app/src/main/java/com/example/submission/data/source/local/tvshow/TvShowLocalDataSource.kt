package com.example.submission.data.source.local.tvshow

import com.example.submission.data.database.dao.TvShowDao
import com.example.submission.domain.entity.tvshow.TvShowEntity
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class TvShowLocalDataSource @Inject constructor(
    private val dao: TvShowDao
) : TvShowLocalService {
    override suspend fun getTvShow(): List<TvShowEntity> {
        return dao.getTvShow()
    }

    override suspend fun insertTvShow(
        tvShows: List<TvShowEntity>
    ) {
        dao.insertTvShow(tvShows)
    }

    override suspend fun getAllTvFavorite(): List<TvShowEntity> {
        return dao.getAllTvFavorite()
    }

    override suspend fun updateTvFavorite(isFavorite: Boolean, tvId: Int) {
        dao.updateFavoriteTv(isFavorite, tvId)
    }
}