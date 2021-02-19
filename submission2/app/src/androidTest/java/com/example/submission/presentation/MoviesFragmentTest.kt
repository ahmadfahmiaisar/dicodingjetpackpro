package com.example.submission.presentation

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.example.submission.R
import com.example.submission.helper.EspressoIdlingResourceWrapper
import com.example.submission.presentation.movies.detail.MovieDetailActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MoviesFragmentTest {

    @get:Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)

    @get:Rule
    val activityRuleDetailMovie = ActivityTestRule(MovieDetailActivity::class.java, false, false)

    @Before
    fun setup() {
        IdlingRegistry.getInstance().register(EspressoIdlingResourceWrapper.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResourceWrapper.idlingResource)
    }

    @Test
    fun moviesFragment() {
        onView(withId(R.id.rvMovie)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovie)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                3
            )
        )
        onView(withId(R.id.rvMovie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                3,
                click()
            )
        )
    }

    @Test
    fun detailMovie() {
        val intent = Intent().putExtra(MovieDetailActivity.INTENT_KEY_MOVIE_ID, 464052)
        activityRuleDetailMovie.launchActivity(intent)
        onView(withId(R.id.tvTitleMovie)).check(matches(isDisplayed()))
    }
}