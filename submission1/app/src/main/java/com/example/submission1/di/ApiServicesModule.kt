package com.example.submission1.di

import com.example.submission1.data.services.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class ApiServicesModule {
    @Provides
    @Singleton
    fun providesMovieService(retrofit: Retrofit): MovieService = retrofit.create(MovieService::class.java)
}