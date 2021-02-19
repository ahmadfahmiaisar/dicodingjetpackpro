package com.example.submission.helper

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResourceWrapper {
    private const val resource = "idling_resource"
    private val countingIdlingResource = CountingIdlingResource(resource)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

    val idlingResource: IdlingResource
        get() = countingIdlingResource
}