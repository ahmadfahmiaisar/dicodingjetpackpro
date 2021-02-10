package com.example.submission1.di

import com.example.submission1.data.repository.MovieRepositoryImpl
import com.example.submission1.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindMovieRepository(repository: MovieRepositoryImpl): MovieRepository
}