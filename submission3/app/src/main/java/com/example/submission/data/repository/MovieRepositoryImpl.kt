package com.example.submission.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.movie.MovieDetailMapper
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.data.source.local.remotemediator.MovieRemoteMediator
import com.example.submission.data.source.local.pagingsource.PagingMovieFavoriteDataSource
import com.example.submission.data.source.local.movie.MovieLocalDataSource
import com.example.submission.data.source.local.movie.remotekey.MovieRemoteKeySource
import com.example.submission.data.source.remote.MovieRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val localDataSource: MovieLocalDataSource,
    private val remoteKeySource: MovieRemoteKeySource
) : MovieRepository {

    @ExperimentalPagingApi
    override suspend fun getAllMovie(): Flow<PagingData<MovieEntity>> {
        val pagingSourceFactory = { localDataSource.getMovie() }
        val pagingConfig = PagingConfig(
            pageSize = 22,
            initialLoadSize = 22,
            enablePlaceholders = false
        )

        return Pager(
            config = pagingConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = MovieRemoteMediator(
                dispatcher,
                remoteDataSource,
                localDataSource,
                remoteKeySource,
                movieMapper
            )
        ).flow
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        val apiResult = remoteDataSource.getMovieDetail(dispatcher.io, movieId)
        return when (apiResult) {
            is Result.Success -> Result.Success(movieDetailMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override fun getAllMovieFavorite(): Flow<PagingData<MovieEntity>> {
        val pagingSourceFactory = { PagingMovieFavoriteDataSource(localDataSource) }

        val pagingLocalConfig = PagingConfig(
            pageSize = 3,
            initialLoadSize = 3
        )
        return Pager(
            config = pagingLocalConfig,
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { it }
    }

    override suspend fun updateFavoriteMovie(isFavorite: Boolean, movieId: Int) {
        return localDataSource.updateFavoriteMovie(isFavorite, movieId)
    }
}