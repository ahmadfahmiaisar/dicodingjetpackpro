package com.example.submission.data.source.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.submission.data.dispatcher.DispatcherProvider
import com.example.submission.data.vo.Resource
import com.example.submission.data.vo.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

abstract class NetworkBoundResourceCoroutines<ResultType, RequestType> constructor(private val dispatcherProvider: DispatcherProvider) {
    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDb()
        result.addSource(dbSource) { data ->
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    protected abstract fun loadFromDb(): LiveData<ResultType>
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response) {
                is Result.Success -> {
                    GlobalScope.launch(dispatcherProvider.io) {
                        saveCallResult(processResponse(response))
                        GlobalScope.launch(dispatcherProvider.ui) {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                }
                is Result.Empty -> {
                    GlobalScope.launch(dispatcherProvider.ui) {
                        result.addSource(loadFromDb()) { newData ->
                            setValue(Resource.success(newData))
                        }
                    }
                }
                is Result.Error -> {
                    onFetchFailed()
                    result.addSource(dbSource) { newData ->
                        setValue(Resource.error(response.errorMessage, newData))
                    }
                }
            }

        }
    }

    abstract fun createCall(): LiveData<Result<RequestType>>
    abstract suspend fun saveCallResult(item: RequestType)
    private fun processResponse(response: Result.Success<RequestType>) = response.data
    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    private fun setValue(newValue: Resource<ResultType>) {
        if (result != newValue) {
            result.value = newValue
        }
    }
}