package com.example.submission.data.source.local.remotemediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.tvshow.TvOnTheAirMapper
import com.example.submission.data.source.local.tvshow.TvShowLocalDataSource
import com.example.submission.data.source.local.tvshow.remotekey.TvShowRemoteKeySource
import com.example.submission.data.source.remote.TvShowRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.remotekey.TvShowRemoteKey
import com.example.submission.domain.entity.tvshow.TvShowEntity
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class TvShowRemoteMediator(
    private val dispatcherProvider: DispatcherProvider,
    private val tvShowRemoteDataSource: TvShowRemoteDataSource,
    private val tvShowLocalDataSource: TvShowLocalDataSource,
    private val tvShowRemoteKeySource: TvShowRemoteKeySource,
    private val tvShowMapper: TvOnTheAirMapper
) : RemoteMediator<Int, TvShowEntity>() {
    companion object {
        const val TV_SHOW_STARTING_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvShowEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MovieRemoteMediator.MOVIE_STARTING_PAGE_INDEX
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
            val apiResult = tvShowRemoteDataSource.getTvOnTheAir(dispatcherProvider.io, page)

            when (apiResult) {
                is Result.Success -> {
                    val tvShow = apiResult.data.results
                    val endOfPaginationReached = page == 5

                    val pageSizeState = page
                    if (loadType == LoadType.REFRESH) {
                        tvShowRemoteKeySource.clearRemoteKeys()
//                        movieLocalDataSource.clearMovie()
                    }

                    val prevKey = if (pageSizeState == TV_SHOW_STARTING_PAGE_INDEX) null else pageSizeState - 1
                    val nextKey = if (endOfPaginationReached) null else pageSizeState + 1
                    val keys = tvShow?.map {
                        TvShowRemoteKey(
                            repoId = it.id ?: 0,
                            prevKey = prevKey ?: 0,
                            nextKey = nextKey ?: 0
                        )
                    }
                    tvShowRemoteKeySource.insertAll(keys ?: emptyList())
                    tvShowLocalDataSource.insertTvShow(tvShowMapper.toEntity(apiResult.data))

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

    private suspend fun getKeyForLastItem(state: PagingState<Int, TvShowEntity>): TvShowRemoteKey? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { repo ->
            tvShowRemoteKeySource.remoteKeysRepoId(repo.tvId)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, TvShowEntity>): TvShowRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { repo ->
            tvShowRemoteKeySource.remoteKeysRepoId(repo.tvId)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, TvShowEntity>
    ): TvShowRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.tvId?.let { repoId ->
                tvShowRemoteKeySource.remoteKeysRepoId(repoId)
            }
        }
    }
}