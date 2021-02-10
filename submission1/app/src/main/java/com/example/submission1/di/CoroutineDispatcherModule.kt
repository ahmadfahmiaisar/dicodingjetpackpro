package com.example.submission1.di

import com.example.submission1.data.dispatcher.CoroutineDispatcherProvider
import com.example.submission1.data.dispatcher.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
interface CoroutineDispatcherModule {
    @Binds
    fun bindDispatcher(dispatcherProvider: CoroutineDispatcherProvider): DispatcherProvider
}