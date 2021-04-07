package com.example.submission.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission.domain.entity.remotekey.TvShowRemoteKey

@Dao
abstract class TvShowRemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(remoteKey: List<TvShowRemoteKey>)

    @Query("SELECT * FROM tvshow_remote_keys WHERE repoId = :repoId")
    abstract suspend fun remoteKeysRepoId(repoId: Int): TvShowRemoteKey

    @Query("DELETE FROM tvshow_remote_keys")
    abstract suspend fun clearRemoteKeys()
}