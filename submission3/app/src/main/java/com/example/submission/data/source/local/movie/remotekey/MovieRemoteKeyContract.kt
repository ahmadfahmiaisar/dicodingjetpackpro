package com.example.submission.data.source.local.movie.remotekey

import com.example.submission.domain.entity.remotekey.MovieRemoteKey

interface MovieRemoteKeyContract {
    suspend fun insertAll(remoteKey: List<MovieRemoteKey>)
    suspend fun remoteKeysRepoId(repoId: Int): MovieRemoteKey?
    suspend fun clearRemoteKeys()
}