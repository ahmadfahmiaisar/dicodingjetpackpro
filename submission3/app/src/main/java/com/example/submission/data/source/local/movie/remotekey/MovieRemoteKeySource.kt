package com.example.submission.data.source.local.movie.remotekey

import com.example.submission.data.database.dao.MovieRemoteKeysDao
import com.example.submission.domain.entity.remotekey.MovieRemoteKey
import javax.inject.Inject

class MovieRemoteKeySource @Inject constructor(private val dao: MovieRemoteKeysDao) :
    MovieRemoteKeyContract {
    override suspend fun insertAll(remoteKey: List<MovieRemoteKey>) {
        return dao.insertAll(remoteKey)
    }

    override suspend fun remoteKeysRepoId(repoId: Int): MovieRemoteKey? {
        return dao.remoteKeysRepoId(repoId)
    }

    override suspend fun clearRemoteKeys() {
        return dao.clearRemoteKeys()
    }
}