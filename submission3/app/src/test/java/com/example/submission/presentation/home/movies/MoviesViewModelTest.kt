package com.example.submission.presentation.home.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.cachedIn
import com.example.submission.domain.entity.movie.MovieEntity
import com.example.submission.domain.usecase.movie.GetAllMovieFavoriteUseCase
import com.example.submission.domain.usecase.movie.GetMovieNowPlayingUseCase
import com.example.submission.domain.usecase.movie.UpdateFavoriteMovieUseCase
import com.example.submission.presentation.movies.MoviesViewModel
import com.example.submission.utils.CoroutinesTestRule
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private var getMovieNowPlayingUseCase = mock(GetMovieNowPlayingUseCase::class.java)

    @Mock
    private var getAllMovieFavoriteUseCase = mock(GetAllMovieFavoriteUseCase::class.java)

    @Mock
    private var updateFavoriteMovieUseCase = mock(UpdateFavoriteMovieUseCase::class.java)

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        viewModel = MoviesViewModel(
            getMovieNowPlayingUseCase,
            getAllMovieFavoriteUseCase,
            updateFavoriteMovieUseCase
        )
    }

    @Test
    fun `get movie now playing`() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val moviesPaging = PagetestDataSources()
            Mockito.`when`(getMovieNowPlayingUseCase.invoke()).thenReturn(moviesPaging)

            viewModel.getAllMovie()
            verify(getMovieNowPlayingUseCase).invoke()

            val movie = viewModel.getAllMovie().cachedIn(viewModel.viewModelScope)
            assertNotNull(movie)
        }

    }

    class PagetestDataSources : PagingSource<Int, MovieEntity>(), Flow<PagingData<MovieEntity>> {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieEntity> {
            return LoadResult.Page(
                dummyMovieEntities(),
                null,
                null
            )
        }

        private fun dummyMovieEntities(): List<MovieEntity> {
            val list = mutableListOf<MovieEntity>()
            MovieEntity(
                0,
                "oke",
                "sip",
                ""
            )
            return list
        }

        @InternalCoroutinesApi
        override suspend fun collect(collector: FlowCollector<PagingData<MovieEntity>>) {
        }
    }

}