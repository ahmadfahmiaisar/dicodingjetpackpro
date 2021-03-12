package com.example.submission.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.movie.MovieDetailMapper
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.data.source.local.movie.MovieLocalDataSource
import com.example.submission.data.source.remote.MovieRemoteDataSource
import com.example.submission.data.source.remote.paging.PagingMovieLocalDataSource
import com.example.submission.data.source.remote.paging.PagingMovieRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val remoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getAllMovie(): Flow<PagingData<MovieNowPlaying>> {
        val pagingConfig = PagingConfig(
            pageSize = PagingMovieRemoteDataSource.PAGE_SIZE,
            initialLoadSize = PagingMovieRemoteDataSource.PAGE_SIZE
        )
        val pagingLocalConfig = PagingConfig(
            pageSize = PagingMovieLocalDataSource.PAGE_SIZE,
            initialLoadSize = PagingMovieLocalDataSource.PAGE_SIZE
        )
        val pagingRemoteSourceFactory = {
            PagingMovieRemoteDataSource(
                dispatcher,
                remoteDataSource
            )
        }
        val pagingLocalSourceFactory = {
            PagingMovieLocalDataSource(localDataSource)
        }
        Timber.tag("ISINYA").d("page size ${pagingConfig.pageSize} page initial ${pagingConfig.initialLoadSize} page pref ${pagingConfig.prefetchDistance} page maxsize ${pagingConfig.maxSize} page jumptreshhold ${pagingConfig.jumpThreshold} oke $pagingConfig")

        return when {
            !localDataSource.getMovie(pagingConfig.prefetchDistance).isNullOrEmpty() -> {
                Timber.tag("ISINYA").d("ya")
                Timber.tag("ISINYA").d("yaaa page size ${pagingConfig.pageSize} page initial ${pagingConfig.initialLoadSize} page pref ${pagingConfig.prefetchDistance} page maxsize ${pagingConfig.maxSize} page jumptreshhold ${pagingConfig.jumpThreshold} oke $pagingConfig")
                Pager(config = pagingLocalConfig, pagingSourceFactory = pagingLocalSourceFactory)
                    .flow
                    .map {
                        it.map { movieResult ->
                            movieMapper.mappingToDomain(movieResult)
                        }
                    }

            }
            else -> {
                Timber.tag("ISINYA").d("okeeeeh page size ${pagingConfig.pageSize} page initial ${pagingConfig.initialLoadSize} page pref ${pagingConfig.prefetchDistance} page maxsize ${pagingConfig.maxSize} page jumptreshhold ${pagingConfig.jumpThreshold} oke $pagingConfig")
                Timber.tag("ISINYA").d("oke")
                Pager(config = pagingConfig, pagingSourceFactory = pagingRemoteSourceFactory)
                    .flow
                    .map {
                        it.map { movieResult ->
                            localDataSource.insertMovie(movieMapper.toEntityResult(movieResult))
                            movieMapper.map(movieResult)
                        }
                    }
            }
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        val apiResult = remoteDataSource.getMovieDetail(dispatcher.io, movieId)
        return when (apiResult) {
            is Result.Success -> Result.Success(movieDetailMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override suspend fun getAllMovieFavorite(): Result<List<MovieEntity>> {
        val localResult = localDataSource.getAllMovieFavorite()
        return when {
            localResult.isNullOrEmpty() -> Result.Error()
            else -> {
                Result.Success(localResult)
            }
        }
    }

    override suspend fun updateFavoriteMovie(isFavorite: Boolean, movieId: Int) {
        return localDataSource.updateFavoriteMovie(isFavorite, movieId)
    }
}