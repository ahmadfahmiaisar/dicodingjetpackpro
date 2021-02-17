package com.example.submission.presentation.home.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieNowPlaying
import com.example.submission.domain.repository.MovieRepository
import com.example.submission.domain.usecase.movie.GetMovieNowPlayingUseCase
import com.example.submission.presentation.movies.MoviesViewModel
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
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
    private val movieRepository = mock(MovieRepository::class.java)

    @Mock
    private lateinit var getMovieNowPlayingUseCase: GetMovieNowPlayingUseCase

    private lateinit var viewModel: MoviesViewModel

    @Before
    fun setup() {
        getMovieNowPlayingUseCase = GetMovieNowPlayingUseCase(movieRepository)
        viewModel = MoviesViewModel(getMovieNowPlayingUseCase)
    }

    @Test
    fun `get movie now playing`() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val dummyListMovie = mutableListOf<MovieNowPlaying>()
            dummyListMovie.add(
                MovieNowPlaying(
                    1,
                    "desc",
                    "",
                    "movie"
                )
            )
            val listMovie = Result.Success(dummyListMovie)
            Mockito.`when`(getMovieNowPlayingUseCase.invoke(UseCase.None)).thenReturn(listMovie)

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
}