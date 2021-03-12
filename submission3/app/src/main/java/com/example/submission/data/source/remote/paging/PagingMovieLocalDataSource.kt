package com.example.submission.data.source.remote.paging

import androidx.paging.PagingSource
import com.example.submission.data.source.local.movie.MovieLocalDataSource
import com.example.submission.domain.entity.movie.MovieEntity
import timber.log.Timber

class PagingMovieLocalDataSource(private val localDataSource: MovieLocalDataSource) :
    PagingSource<Int, MovieEntity>() {
    companion object {
        const val PAGE_SIZE = 20
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
        return try {
            val currentKey = params.key ?: 1
            val previousKey = if (currentKey == 1) null else currentKey - 1
            val nextKey = currentKey + 1
            val localResult = localDataSource.getMovie(params.loadSize)
            Timber.tag("ISINYAACO").d("current key $currentKey nextkey $nextKey")
            return LoadResult.Page(
                data = localResult,
                prevKey = previousKey,
                nextKey = nextKey
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}