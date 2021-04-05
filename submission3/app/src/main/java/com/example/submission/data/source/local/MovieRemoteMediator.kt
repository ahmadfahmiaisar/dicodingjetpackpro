package com.example.submission.data.source.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.data.source.local.movie.MovieLocalDataSource
import com.example.submission.data.source.local.movie.remotekey.MovieRemoteKeySource
import com.example.submission.data.source.remote.MovieRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.entity.remotekey.MovieRemoteKey
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@ExperimentalPagingApi
class MovieRemoteMediator(
    private val dispatcherProvider: DispatcherProvider,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteKey: MovieRemoteKeySource,
    private val movieMapper: MovieMapper
) : RemoteMediator<Int, MovieEntity>() {
    companion object {
        const val MOVIE_STARTING_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MOVIE_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)

                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getKeyForLastItem(state)

                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                nextKey
            }
        }

        try {
            val apiResponse = movieRemoteDataSource.getMovieNowPlaying(
                dispatcherProvider.io,
                page
            )
            Timber.tag("ISINYA").d("load: $page and ${state.config.pageSize}")
            when (apiResponse) {
                is Result.Success -> {
                    val movies = apiResponse.data.results
                    val endOfPaginationReached = page == 5

                    val pageSizeState = page
                    if (loadType == LoadType.REFRESH) {
                        movieRemoteKey.clearRemoteKeys()
                        movieLocalDataSource.clearMovie()
                    }

                    val prevKey = if (pageSizeState == MOVIE_STARTING_PAGE_INDEX) null else pageSizeState - 1
                    val nextKey = if (endOfPaginationReached) null else pageSizeState + 1
                    val keys = movies.map {
                        MovieRemoteKey(
                            repoId = it.id ?: 0,
                            prevKey = prevKey ?: 0,
                            nextKey = nextKey ?: 0
                        )
                    }
                    movieRemoteKey.insertAll(keys)
                    movieLocalDataSource.insertMovie(movieMapper.toEntity(apiResponse.data))

                    return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                }
                else -> return MediatorResult.Error(Throwable())
            }
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyForLastItem(state: PagingState<Int, MovieEntity>): MovieRemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            movieRemoteKey.remoteKeysRepoId(repo.movieId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieEntity>): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
            movieRemoteKey.remoteKeysRepoId(repo.movieId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieEntity>
    ): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieId?.let { repoId ->
                movieRemoteKey.remoteKeysRepoId(repoId)
            }
        }
    }
}