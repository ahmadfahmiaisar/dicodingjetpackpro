package com.example.submission.domain.entity.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_keys")
data class MovieRemoteKey(
    @PrimaryKey var repoId: Int = 0,
    var prevKey: Int = 0,
    var nextKey: Int = 0
)