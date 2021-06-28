package com.example.submission.di

import android.content.Context
import androidx.room.Room
import com.example.submission.data.database.AppDatabase
import com.example.submission.data.database.dao.MovieDao
import com.example.submission.data.database.dao.MovieRemoteKeysDao
import com.example.submission.data.database.dao.TvShowDao
import com.example.submission.data.database.dao.TvShowRemoteKeysDao
import com.example.submission.data.vo.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constant.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideTvShowDao(db: AppDatabase): TvShowDao = db.tvShowDao()

    @Provides
    @Singleton
    fun providesMovieRemotekeys(db: AppDatabase): MovieRemoteKeysDao = db.movieRemoteKeyDao()

    @Provides
    @Singleton
    fun providesTvShowRemoteKeys(db:AppDatabase): TvShowRemoteKeysDao = db.tvShowRemoteKeyDao()
}