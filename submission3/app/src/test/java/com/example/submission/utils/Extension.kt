package com.example.submission.utils

import androidx.lifecycle.LiveData

fun <T> LiveData<T>.observerTest(onChangeHandler: (T) -> Unit) {
    val observer = LifeCycleTestObserver(onChangeHandler)
    observe(observer, observer)
}
