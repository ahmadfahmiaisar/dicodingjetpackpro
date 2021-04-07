package com.example.submission.data.source.local.tvshow.remotekey

import com.example.submission.data.database.dao.TvShowRemoteKeysDao
import com.example.submission.domain.entity.remotekey.TvShowRemoteKey
import javax.inject.Inject

class TvShowRemoteKeySource @Inject constructor(private val dao: TvShowRemoteKeysDao) :
    TvShowRemoteKeyContract {

    override suspend fun insertAll(remoteKey: List<TvShowRemoteKey>) {
        return dao.insertAll(remoteKey)
    }

    override suspend fun remoteKeysRepoId(repoId: Int): TvShowRemoteKey? {
        return dao.remoteKeysRepoId(repoId)
    }

    override suspend fun clearRemoteKeys() {
        return dao.clearRemoteKeys()
    }
}