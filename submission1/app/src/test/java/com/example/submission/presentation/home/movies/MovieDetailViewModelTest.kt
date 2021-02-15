package com.example.submission.presentation.home.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.submission.abstraction.UseCase
import com.example.submission.data.vo.Result
import com.example.submission.domain.entity.movie.MovieDetail
import com.example.submission.domain.entity.movie.MovieDetailUiModel
import com.example.submission.domain.repository.MovieRepository
import com.example.submission.domain.usecase.movie.GetMovieDetailUseCase
import com.example.submission.domain.usecase.movie.GetMovieNowPlayingUseCase
import com.example.submission.presentation.home.movies.detail.MovieDetailViewModel
import com.example.submission.utils.CoroutinesTestRule
import com.example.submission.utils.observerTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import timber.log.Timber

@ExperimentalCoroutinesApi
class MovieDetailViewModelTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    private val repository = Mockito.mock(MovieRepository::class.java)

    @Mock
    private lateinit var getMovieDetailUseCase: GetMovieDetailUseCase

    private lateinit var viewModel: MovieDetailViewModel

    @Before
    fun setup() {
        getMovieDetailUseCase = GetMovieDetailUseCase(repository)
        viewModel = MovieDetailViewModel(getMovieDetailUseCase)
    }

    @Test
    fun getMovieDetail() {
        coroutinesTestRule.dispatcher.runBlockingTest {
            val dummyList = MovieDetail(1, "desc", 0f, "", "date", "title", 0f, 0)

            val listDetailMovieSucces = Result.Success(dummyList)
            Mockito.`when`(getMovieDetailUseCase.invoke(1)).thenReturn(listDetailMovieSucces)

            viewModel.getMovieDetail(1)
            viewModel.movieDetail.observerTest {

                when (it) {
                    is Result.Loading -> {
                        Timber.tag("ISINYA")
                    }
                    is Result.Success -> {
                        assertNotNull(it.data)
                        assertEquals(dummyList.id, it.data.id)
                    }
                    is Result.Error -> {
                        Timber.tag("ISINYA")
                    }
                    else -> {
                        Timber.tag("ISINYA").d("$it")
                    }
                }

            }
        }
    }
}