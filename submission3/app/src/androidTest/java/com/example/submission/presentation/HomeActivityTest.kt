package com.example.submission.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.example.submission.R
import kotlinx.android.synthetic.main.activity_home.*
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private lateinit var activity: HomeActivity

    @JvmField
    @Rule
    val activityRule = ActivityTestRule(HomeActivity::class.java)
    private lateinit var adapter: HomeActivity.ViewPagerAdapter

    @Before
    fun setup() {
        activity = activityRule.activity
        assertThat(activity, notNullValue())

        adapter = (activity.viewPager.adapter) as HomeActivity.ViewPagerAdapter
    }

    @Test
    fun checkTabFirstTitle() {
        onView(allOf(withText("MOVIES"), isDescendantOfA(withId(R.id.tabLayout))))
            .perform(click())
            .check(matches(isDisplayed()))

        val title = adapter.getPageTitle(0)
        assertThat(title.toString(), Matchers.equalTo("MOVIES"))

    }

    @Test
    fun checkSwipeViewPager() {
        onView(withId(R.id.viewPager)).check(matches(isDisplayed()))
        onView(withId(R.id.viewPager)).perform(swipeLeft())
        onView(withId(R.id.viewPager)).perform(swipeRight())
    }
}