package com.example.submission.presentation.home.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.usecase.movie.GetMovieDetailUseCase
import com.example.submission.presentation.movies.detail.MovieDetailViewModel
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val getMovieDetailUseCase = mock(GetMovieDetailUseCase::class.java)

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setup() {
        viewModel = MovieDetailViewModel(getMovieDetailUseCase)
    }

    @Test
    fun getMovieDetailTest1() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val movieId = 464052

            val dummy = dummyDetailMovie()
            Mockito.`when`(getMovieDetailUseCase.invoke(movieId)).thenReturn(dummy)
            viewModel.getMovieDetail(movieId)
            Mockito.verify(getMovieDetailUseCase).invoke(movieId)

        }
    }


    @Test
    fun getMovieDetail() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val movieId = 1
            val detailMovie = MutableLiveData<Result<MovieDetail>>()
            detailMovie.value = dummyDetailMovie()

            Mockito.`when`(getMovieDetailUseCase.invoke(movieId)).thenReturn(detailMovie.value)
            viewModel.getMovieDetail(movieId)
            Mockito.verify(getMovieDetailUseCase).invoke(movieId)

            viewModel.movieDetail.observerTest {
                when (it) {
                    is Result.Success -> {
                        Assert.assertNotNull(it.data)
                        Assert.assertEquals(movieId, it.data.id)
                    }
                }
            }
        }
    }

    private fun dummyDetailMovie(): Result<MovieDetail> {
        return Result.Success(
            MovieDetail(1, "desc", 0f, "", "date", "title", 0f, 0)
        )
    }
}