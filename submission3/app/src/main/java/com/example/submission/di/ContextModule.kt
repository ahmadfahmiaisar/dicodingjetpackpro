package com.example.submission.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(ApplicationComponent::class)
@Module
class ContextModule {
    @Provides
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }
}