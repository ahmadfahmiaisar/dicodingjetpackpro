package com.example.submission.domain.entity.remotekey

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshow_remote_keys")
data class TvShowRemoteKey(
    @PrimaryKey var repoId: Int = 0,
    var prevKey: Int = 0,
    var nextKey: Int = 0
)