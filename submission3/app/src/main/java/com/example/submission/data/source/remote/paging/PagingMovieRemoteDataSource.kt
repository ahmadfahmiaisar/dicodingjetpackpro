package com.example.submission.data.source.remote.paging

import androidx.paging.PagingSource
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.response.movie.NowPlayingDto
import com.example.submission.data.source.remote.MovieRemoteDataSource
import com.example.submission.data.vo.Result
import javax.inject.Inject

class PagingMovieRemoteDataSource @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val movieRemoteDataSource: MovieRemoteDataSource
) : PagingSource<Int, NowPlayingDto.Result>() {
    companion object {
        const val PAGE_SIZE = 20
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NowPlayingDto.Result> {
        return try {
            val currentKey = params.key ?: 1
            val previousKey = if (currentKey == 1) null else currentKey - 1
            val nextKey = currentKey + 1
            val apiResult =
                movieRemoteDataSource.getMovieNowPlaying(dispatcherProvider.io, currentKey)
            return when (apiResult) {
                is Result.Success -> {
                    if (apiResult.data.results.isNullOrEmpty()) {
                        LoadResult.Page(
                            data = emptyList(),
                            prevKey = null,
                            nextKey = null
                        )
                    } else {
                        LoadResult.Page(
                            data = apiResult.data.results,
                            prevKey = previousKey,
                            nextKey = nextKey
                        )
                    }

                }
                is Result.Error -> LoadResult.Error(Exception())
                else -> LoadResult.Error(Exception())
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}