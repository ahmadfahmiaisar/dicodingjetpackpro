package com.example.submission.data.repository

import com.example.submission.data.mapper.tvshow.TvOnTheAirMapper
import com.example.submission.data.mapper.tvshow.TvShowDetailMapper
import com.example.submission.data.response.tvshow.TvOnTheAirDto
import com.example.submission.data.response.tvshow.TvShowDetailDto
import com.example.submission.data.source.TvShowRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class TvShowRepositoryImplTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val remoteDataSource = Mockito.mock(TvShowRemoteDataSource::class.java)

    private lateinit var tvShowRepositoryImpl: TvShowRepositoryImpl

    private var tvOnTheAirMapper = Mockito.mock(TvOnTheAirMapper::class.java)

    private var tvShowDetailMapper = Mockito.mock(TvShowDetailMapper::class.java)

    @Before
    fun setup() {
        tvShowRepositoryImpl = TvShowRepositoryImpl(
            coroutinesTestRule.testDispatcherProvider,
            remoteDataSource,
            tvOnTheAirMapper,
            tvShowDetailMapper
        )
    }

    @Test
    fun getTvShow() {
        coroutinesTestRule.runBlockingTest {
            val response = Result.Success(fakeTvShowResponse())
            Mockito.`when`(remoteDataSource.getTvOnTheAir(coroutinesTestRule.dispatcher))
                .thenReturn(response)

            tvShowRepositoryImpl.getTvOnTheAir()
            verify(remoteDataSource).getTvOnTheAir(coroutinesTestRule.dispatcher)
            Assert.assertNotNull(tvShowRepositoryImpl.getTvOnTheAir())
            Assert.assertEquals(1, response.data.results?.size)
        }
    }

    @Test
    fun getDetailTvShow() {
        coroutinesTestRule.runBlockingTest {
            val tvId = 1
            val response = Result.Success(fakeDetailTvResponse())
            Mockito.`when`(remoteDataSource.getTvShowDetail(coroutinesTestRule.dispatcher, tvId))
                .thenReturn(response)

            tvShowRepositoryImpl.getTvShowDetail(tvId)
            Mockito.verify(remoteDataSource).getTvShowDetail(coroutinesTestRule.dispatcher, tvId)
            Assert.assertNotNull(tvShowRepositoryImpl.getTvShowDetail(tvId))
            Assert.assertEquals(1, response.data.id)
        }
    }

    private fun fakeTvShowResponse(): TvOnTheAirDto {
        val result = TvOnTheAirDto.Result(0, "", "", "")
        return TvOnTheAirDto(mutableListOf(result))
    }

    private fun fakeDetailTvResponse(): TvShowDetailDto {
        return TvShowDetailDto(1, "", "", 0.0, "", "", 0.0, 0)
    }
}