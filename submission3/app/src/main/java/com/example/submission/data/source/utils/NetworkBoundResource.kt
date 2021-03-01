package com.example.submission.data.source.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submission.data.vo.Result

abstract class NetworkBoundResource<ResultType, RequestType> constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Result<ResultType>>()

    init {
        result.value = Result.Loading

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) {data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Result.Success(newData)
                }
            }
        }
    }

    protected abstract fun loadFromDB(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()

        result.addSource(dbSource) {
            result.value = Result.Loading
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is Result.Success -> {
                    appExecutors.diskIO().execute {
                        saveCallResult(response.data)
                    }
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDB()) { newData ->
                            result.value = Result.Success(newData)
                        }
                    }
                }
                is Result.Empty -> {
                    appExecutors.mainThread().execute {
                        result.addSource(loadFromDB()) { newData ->
                            result.value = Result.Success(newData)
                        }
                    }
                }
                is Result.Error -> {
                    onFetchFailed()
                    result.addSource(dbSource) {
                        result.value =
                            Result.Error(response.cause, response.code, response.errorMessage)
                    }
                }
            }
        }
    }

    protected abstract fun createCall(): LiveData<Result<RequestType>>
    protected abstract fun saveCallResult(data: RequestType)
    protected open fun onFetchFailed() {}

    fun asLiveData(): LiveData<Result<ResultType>> = result
}