package com.example.submission.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.example.submission.data.mapper.tvshow.TvOnTheAirMapper
import com.example.submission.data.mapper.tvshow.TvShowDetailMapper
import com.example.submission.data.response.tvshow.TvOnTheAirDto
import com.example.submission.data.response.tvshow.TvShowDetailDto
import com.example.submission.data.source.local.tvshow.TvShowLocalDataSource
import com.example.submission.data.source.local.tvshow.remotekey.TvShowRemoteKeySource
import com.example.submission.data.source.remote.TvShowRemoteDataSource
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class TvShowRepositoryImplTest {
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val remoteDataSource = mock(TvShowRemoteDataSource::class.java)

    private val localDataSource = mock(TvShowLocalDataSource::class.java)

    private val remoteKeySource = mock(TvShowRemoteKeySource::class.java)

    private lateinit var tvShowRepositoryImpl: TvShowRepositoryImpl

    private var tvOnTheAirMapper = mock(TvOnTheAirMapper::class.java)

    private var tvShowDetailMapper = mock(TvShowDetailMapper::class.java)

    @Before
    fun setup() {
        tvShowRepositoryImpl = TvShowRepositoryImpl(
            coroutinesTestRule.testDispatcherProvider,
            remoteDataSource,
            tvOnTheAirMapper,
            tvShowDetailMapper,
            localDataSource,
            remoteKeySource
        )
    }

    @ExperimentalPagingApi
    @Test
    fun getTvShow() {
        coroutinesTestRule.runBlockingTest {
            val response = Result.Success(fakeTvShowResponse())
            val dataSourceFactory =
                mock(PagingSource::class.java) as PagingSource<Int, TvShowEntity>
            Mockito.`when`(localDataSource.getTvShow())
                .thenReturn(dataSourceFactory)

            tvShowRepositoryImpl.getTvOnTheAir()

            localDataSource.getTvShow()
            verify(localDataSource).getTvShow()
            Assert.assertNotNull(tvShowRepositoryImpl.getTvOnTheAir())
            Assert.assertEquals(1, response.data.results?.size)
        }
    }

    @Test
    fun getTvFavorite() {
        coroutinesTestRule.runBlockingTest {
            val dataCallback = fakeListTvEntity()
            Mockito.`when`(localDataSource.getAllTvFavorite()).thenReturn(
                dataCallback
            )
            tvShowRepositoryImpl.getAllTvFavorite()

            localDataSource.getAllTvFavorite()
            verify(localDataSource).getAllTvFavorite()
            Assert.assertNotNull(localDataSource.getAllTvFavorite())
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
            verify(remoteDataSource).getTvShowDetail(coroutinesTestRule.dispatcher, tvId)
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
    private fun fakeListTvEntity(): List<TvShowEntity> {
        val listTv = mutableListOf<TvShowEntity>()
        listTv.add(
            TvShowEntity(
                0, "title", "poster", "overview", false
            )
        )
        return listTv
    }
}