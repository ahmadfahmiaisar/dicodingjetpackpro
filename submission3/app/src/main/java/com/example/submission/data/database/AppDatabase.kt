package com.example.submission.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submission.data.database.dao.MovieDao
import com.example.submission.data.database.dao.MovieRemoteKeysDao
import com.example.submission.data.database.dao.TvShowDao
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.entity.remotekey.MovieRemoteKey
import com.example.submission.domain.entity.tvshow.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class, MovieRemoteKey::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
    abstract fun movieRemoteKeyDao(): MovieRemoteKeysDao
}