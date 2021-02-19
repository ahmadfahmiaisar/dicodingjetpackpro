package com.example.submission.presentation.home.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.usecase.movie.GetMovieNowPlayingUseCase
import com.example.submission.presentation.movies.MoviesViewModel
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MoviesViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private var getMovieNowPlayingUseCase = mock(GetMovieNowPlayingUseCase::class.java)

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        viewModel = MoviesViewModel(getMovieNowPlayingUseCase)
    }

    @Test
    fun `get movie now playing`() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val movies = MutableLiveData<Result<List<MovieNowPlaying>>>()
            movies.value = dummyMovies()
            Mockito.`when`(getMovieNowPlayingUseCase.invoke(UseCase.None)).thenReturn(movies.value)
            verify(getMovieNowPlayingUseCase, never()).invoke(UseCase.None)

            viewModel.getMovie()
            viewModel.movie.observerTest {
                when (it) {
                    is Result.Success -> {
                        assertNotNull(it.data)
                        assertEquals(1, it.data.size)
                    }
                }
            }
        }

    }

    private fun dummyMovies(): Result<List<MovieNowPlaying>> {
        val dummyListMovie = mutableListOf<MovieNowPlaying>()
        dummyListMovie.add(
            MovieNowPlaying(
                1,
                "desc",
                "",
                "movie"
            )
        )
        return Result.Success(dummyListMovie)
    }
}