package com.example.submission.di

import com.example.submission.data.services.MovieService
import com.example.submission.data.services.TvShowService
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
    fun providesMovieService(retrofit: Retrofit): MovieService =
        retrofit.create(MovieService::class.java)

    @Provides
    @Singleton
    fun providesTvShowService(retrofit: Retrofit): TvShowService =
        retrofit.create(TvShowService::class.java)
}