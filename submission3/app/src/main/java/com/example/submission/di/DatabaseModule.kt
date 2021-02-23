package com.example.submission.di

import android.content.Context
import androidx.room.Room
import com.example.submission.data.database.AppDatabase
import com.example.submission.data.database.dao.MovieDao
import com.example.submission.data.database.dao.TvShowDao
import com.example.submission.data.source.local.MovieLocalDataSource
import com.example.submission.data.source.local.MovieLocalService
import com.example.submission.data.vo.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, Constant.DATABASE_NAME).build()

   /* @Provides
    @ActivityScoped
    fun providesMovieLocalDataSource(appDatabase: AppDatabase): MovieLocalService {
        return MovieLocalDataSource(appDatabase)
    }*/
    /* @Provides
     @FeatureScope
     fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao() */

    @Provides
    @Singleton
    fun provideMovieDao(db: AppDatabase): MovieDao = db.movieDao()

    @Provides
    @Singleton
    fun provideTvShowDao(db: AppDatabase): TvShowDao = db.tvShowDao()
}