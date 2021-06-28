package com.example.submission.data.source.local.pagingsource

import androidx.paging.PagingSource
import com.example.submission.data.source.local.movie.MovieLocalDataSource
import com.example.submission.domain.entity.movie.MovieEntity

class PagingMovieFavoriteDataSource(
  private val localDataSource: MovieLocalDataSource
): PagingSource<Int, MovieEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        return try {
            val currentKey = params.key ?: 1
            val previousKey = if (currentKey == 1) null else currentKey - 1
            val localResult = localDataSource.getAllMovieFavorite()
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