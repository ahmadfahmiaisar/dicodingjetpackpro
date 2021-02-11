package com.example.submission.di

import com.example.submission.data.repository.MovieRepositoryImpl
import com.example.submission.data.repository.TvShowRepositoryImpl
import com.example.submission.domain.repository.MovieRepository
import com.example.submission.domain.repository.TvShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository

    @Binds
    fun bindTvShowRepository(repository: TvShowRepositoryImpl): TvShowRepository
}