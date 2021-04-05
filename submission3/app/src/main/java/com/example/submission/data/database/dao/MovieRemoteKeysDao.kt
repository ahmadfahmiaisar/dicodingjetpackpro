package com.example.submission.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.submission.domain.entity.remotekey.MovieRemoteKey

@Dao
abstract class MovieRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertAll(remoteKey: List<MovieRemoteKey>)

    @Query("SELECT * FROM movie_remote_keys WHERE repoId = :repoId")
    abstract suspend fun remoteKeysRepoId(repoId: Int): MovieRemoteKey?

    @Query("DELETE FROM movie_remote_keys")
    abstract suspend fun clearRemoteKeys()
}