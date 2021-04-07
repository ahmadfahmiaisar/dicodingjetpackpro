package com.example.submission.data.source.local.tvshow.remotekey

import com.example.submission.domain.entity.remotekey.TvShowRemoteKey

interface TvShowRemoteKeyContract {
    suspend fun insertAll(remoteKey: List<TvShowRemoteKey>)
    suspend fun remoteKeysRepoId(repoId: Int): TvShowRemoteKey?
    suspend fun clearRemoteKeys()
}