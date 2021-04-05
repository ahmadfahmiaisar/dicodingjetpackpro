package com.example.submission.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.example.submission.data.mapper.movie.MovieDetailMapper
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.data.response.movie.MovieDetailDto
import com.example.submission.data.response.movie.NowPlayingDto
import com.example.submission.data.source.local.movie.MovieLocalDataSource
import com.example.submission.data.source.local.movie.remotekey.MovieRemoteKeySource
import com.example.submission.data.source.remote.MovieRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val remoteDataSource = mock(MovieRemoteDataSource::class.java)

    private val localDataSource = mock(MovieLocalDataSource::class.java)

    private val remoteKeySource = mock(MovieRemoteKeySource::class.java)

    private lateinit var movieRepositoryImpl: MovieRepositoryImpl

    private var movieMapper = mock(MovieMapper::class.java)

    private var detailMapper = mock(MovieDetailMapper::class.java)

    @Before
    fun setup() {
        movieRepositoryImpl = MovieRepositoryImpl(
            coroutinesTestRule.testDispatcherProvider,
            remoteDataSource,
            movieMapper,
            detailMapper,
            localDataSource,
            remoteKeySource
        )
    }

    @ExperimentalPagingApi
    @Test
    fun getMoviePaging() {
        coroutinesTestRule.runBlockingTest {
            val response = Result.Success(fakeMoviesResponse())
            val dataSourceFactory = mock(PagingSource::class.java) as PagingSource<Int, MovieEntity>
            `when`(localDataSource.getMovie()).thenReturn(
                dataSourceFactory
            )
            movieRepositoryImpl.getAllMovie()

            verify(localDataSource.getMovie())
            Assert.assertNotNull(movieRepositoryImpl.getAllMovie())
            Assert.assertEquals(1, response.data.results.size)
        }
    }

    @Test
    fun getDetailMovie() {
        coroutinesTestRule.runBlockingTest {
            val movieId = 1
            val response = Result.Success(fakeDetailMovieResponse())
            `when`(
                remoteDataSource.getMovieDetail(
                    coroutinesTestRule.dispatcher,
                    movieId
                )
            ).thenReturn(response)

            movieRepositoryImpl.getMovieDetail(movieId)

            verify(remoteDataSource).getMovieDetail(coroutinesTestRule.dispatcher, movieId)
            Assert.assertNotNull(movieRepositoryImpl.getMovieDetail(movieId))
            Assert.assertEquals(1, response.data.id)
        }
    }

    private fun fakeMoviesResponse(): NowPlayingDto {
        val result = NowPlayingDto.Result(0, "", "", "")
        return NowPlayingDto(mutableListOf(result))
    }

    private fun fakeDetailMovieResponse(): MovieDetailDto {
        return MovieDetailDto(1, "", 0f, "", "", "", 0f, 0)
    }
}