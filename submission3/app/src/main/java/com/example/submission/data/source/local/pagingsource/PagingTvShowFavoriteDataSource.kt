package com.example.submission.data.source.local.pagingsource

import androidx.paging.PagingSource
import com.example.submission.data.source.local.tvshow.TvShowLocalDataSource
import com.example.submission.domain.entity.tvshow.TvShowEntity

class PagingTvShowFavoriteDataSource(
    private val localDataSource: TvShowLocalDataSource
) : PagingSource<Int, TvShowEntity>(){
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowEntity> {
        return try {
            val currentKey = params.key ?: 1
            val previousKey = if (currentKey == 1) null else currentKey - 1
            val localResult = localDataSource.getAllTvFavorite()
            return LoadResult.Page(
                data = localResult,
                previousKey,
                null
            )
        } catch (exception: Exception){
            LoadResult.Error(exception)
        }
    }
}