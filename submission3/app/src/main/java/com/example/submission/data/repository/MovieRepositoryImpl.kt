package com.example.submission.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.mapper.movie.MovieDetailMapper
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.data.response.movie.NowPlayingDto
import com.example.submission.data.source.local.movie.MovieLocalDataSource
import com.example.submission.data.source.remote.MovieRemoteDataSource
import com.example.submission.data.source.remote.paging.PagingMovieRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val dispatcher: DispatcherProvider,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieMapper: MovieMapper,
    private val movieDetailMapper: MovieDetailMapper,
    private val movieLocalDataSource: MovieLocalDataSource
) : MovieRepository {
   /* override suspend fun getMovieNowPlaying(): Result<List<MovieNowPlaying>> {
        val apiResult = movieRemoteDataSource.getMovieNowPlaying(dispatcher.io)
        val localResult = movieLocalDataSource.getMovie()
        return when {
            !localResult.isNullOrEmpty() -> {
                Result.Success(movieMapper.mapToDomain(localResult))
            }
            else -> {
                when (apiResult) {
                    is Result.Success -> {
                        movieLocalDataSource.insertMovie(movieMapper.toEntity(apiResult.data))
                        Result.Success(movieMapper.map(apiResult.data))
                    }
                    is Result.Error -> Result.Error(
                        apiResult.cause,
                        apiResult.code,
                        apiResult.errorMessage
                    )
                    else -> Result.Error()
                }
            }
        }

    }*/

    override fun getAllMovie(): Flow<PagingData<MovieNowPlaying>> {
        val pagingConfig = PagingConfig(
            pageSize = PagingMovieRemoteDataSource.PAGE_SIZE,
            initialLoadSize = PagingMovieRemoteDataSource.PAGE_SIZE
        )
        val pagingSourceFactory = {
            PagingMovieRemoteDataSource(
                dispatcher,
                movieRemoteDataSource
            )
        }
        return Pager(config = pagingConfig, pagingSourceFactory = pagingSourceFactory)
            .flow
            .map {
                it.map { movieResult ->
                    movieMapper.map(movieResult)
                }
            }
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> {
        val apiResult = movieRemoteDataSource.getMovieDetail(dispatcher.io, movieId)
        return when (apiResult) {
            is Result.Success -> Result.Success(movieDetailMapper.map(apiResult.data))
            is Result.Error -> Result.Error(apiResult.cause, apiResult.code, apiResult.errorMessage)
            else -> Result.Error()
        }
    }

    override suspend fun getAllMovieFavorite(): Result<List<MovieEntity>> {
        val localResult = movieLocalDataSource.getAllMovieFavorite()
        return when {
            localResult.isNullOrEmpty() -> Result.Error()
            else -> {
                Result.Success(localResult)
            }
        }
    }

    override suspend fun updateFavoriteMovie(isFavorite: Boolean, movieId: Int) {
        return movieLocalDataSource.updateFavoriteMovie(isFavorite, movieId)
    }
}