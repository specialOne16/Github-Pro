package com.jundapp.githubpro.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.jundapp.githubpro.core.data.source.remote.network.ApiResponse
import com.jundapp.githubpro.core.utils.AppExecutors

abstract class NetworkOnlyResource<ResultType, RequestType>(private val mExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.Loading(null)
        fetchFromNetwork()
    }

    protected open fun onFetchFailed() {}

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun mapType(request: RequestType): ResultType

    protected abstract fun loadEmpty(): ResultType

    private fun fetchFromNetwork() {

        val apiResponse = createCall()

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            when (response) {
                is ApiResponse.Success ->
                    result.value = Resource.Success(mapType(response.data))
                is ApiResponse.Empty -> result.value = Resource.Success(loadEmpty())
                is ApiResponse.Error -> {
                    onFetchFailed()
                    result.value = Resource.Error(response.errorMessage, loadEmpty())
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result
}