package com.example.submission.domain.entity.tvshow

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.submission.domain.entity.tvshow.TvOnTheAir

@Entity(tableName = "tvshows")
data class TvShowEntity(
    @PrimaryKey
    @ColumnInfo(name = "tvId")
    var tvId: String = "",

    @ColumnInfo(name = "name")
    var name: String = "",

    @ColumnInfo(name = "posterPath")
    var posterPath: String = "",

    @ColumnInfo(name = "overview")
    var overview: String = "",

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false
)