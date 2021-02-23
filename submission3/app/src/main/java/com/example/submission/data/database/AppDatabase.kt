package com.example.submission.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.submission.data.database.dao.MovieDao
import com.example.submission.data.database.dao.TvShowDao
import com.example.submission.data.database.entity.MovieEntity
import com.example.submission.data.database.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao
}