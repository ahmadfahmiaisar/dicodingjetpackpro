package com.example.submission.presentation.home.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.entity.tvshow.TvShowEntity
import com.example.submission.domain.usecase.tvshow.GetAllTvFavoriteUseCase
import com.example.submission.domain.usecase.tvshow.GetTvOnTheAirUseCase
import com.example.submission.domain.usecase.tvshow.UpdateFavoriteTvUseCase
import com.example.submission.presentation.tvshows.TvShowsViewModel
import com.example.submission.utils.CoroutinesTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class TvShowsViewModelTest {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val getTvOnTheAirUseCase = mock(GetTvOnTheAirUseCase::class.java)

    @Mock
    private val getAllTvFavoriteUseCase = mock(GetAllTvFavoriteUseCase::class.java)

    @Mock
    private val updateFavoriteTvUseCase = mock(UpdateFavoriteTvUseCase::class.java)

    private lateinit var viewModel: TvShowsViewModel


    @Before
    fun setup() {
        viewModel =
            TvShowsViewModel(getTvOnTheAirUseCase, getAllTvFavoriteUseCase, updateFavoriteTvUseCase)
    }

    @Test
    fun addTvFavorite() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val tvId = 456
            Mockito.`when`(updateFavoriteTvUseCase.invoke(true, tvId)).thenReturn(Unit)

            viewModel.setStatusFavoriteMovie(true, tvId)
            verify(updateFavoriteTvUseCase).invoke(true, tvId)
            assertEquals(viewModel.areStatusFavorite.value, Unit)
        }
    }

    @Test
    fun getTvShows() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val tvShow = PagetestDataSources()
            Mockito.`when`(getTvOnTheAirUseCase.invoke()).thenReturn(tvShow)
            viewModel.getTvShows()
            Mockito.verify(getTvOnTheAirUseCase).invoke()
            val tvshows = viewModel.getTvShows().cachedIn(viewModel.viewModelScope)
            assertNotNull(tvshows)
        }
    }

    class PagetestDataSources : PagingSource<Int, TvShowEntity>(), Flow<PagingData<TvShowEntity>> {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowEntity> {
            return LoadResult.Page(
                dummyMovieEntities(),
                null,
                null
            )
        }

        private fun dummyMovieEntities(): List<TvShowEntity> {
            val list = mutableListOf<TvShowEntity>()
            MovieEntity(
                0,
                "oke",
                "sip",
                ""
            )
            return list
        }

        @InternalCoroutinesApi
        override suspend fun collect(collector: FlowCollector<PagingData<TvShowEntity>>) {
        }
    }

}