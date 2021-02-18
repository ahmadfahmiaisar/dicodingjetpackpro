package com.example.submission.data.repository

import com.example.submission.data.mapper.movie.MovieDetailMapper
import com.example.submission.data.mapper.movie.MovieMapper
import com.example.submission.data.response.movie.NowPlayingDto
import com.example.submission.data.source.MovieRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val remoteDataSource = Mockito.mock(MovieRemoteDataSource::class.java)

    private lateinit var movieRepositoryImpl: MovieRepositoryImpl

    private var movieMapper = Mockito.mock(MovieMapper::class.java)

    private var detailMapper = Mockito.mock(MovieDetailMapper::class.java)

    @Before
    fun setup() {
        movieRepositoryImpl = MovieRepositoryImpl(
            coroutinesTestRule.testDispatcherProvider,
            remoteDataSource,
            movieMapper,
            detailMapper
        )
    }

    @Test
    fun getMovie() {
        coroutinesTestRule.runBlockingTest {
            val response = Result.Success(fakeMoviesResponse())
            `when`(remoteDataSource.getMovieNowPlaying(coroutinesTestRule.dispatcher)).thenReturn(
                response
            )
            movieRepositoryImpl.getMovieNowPlaying()

            verify(remoteDataSource).getMovieNowPlaying(coroutinesTestRule.dispatcher)
            Assert.assertNotNull(movieRepositoryImpl.getMovieNowPlaying())
            Assert.assertEquals(1, response.data.results?.size)
        }
    }

    private fun fakeMoviesResponse(): NowPlayingDto {
        val result = NowPlayingDto.Result(0, "", "", "")
        return NowPlayingDto(mutableListOf(result))
    }
}