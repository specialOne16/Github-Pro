package com.jundapp.githubpro.core.data

import com.jundapp.githubpro.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

abstract class NetworkOnlyResource<ResultType, RequestType> {

    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        when (val apiResponse = createCall().first()) {
            is ApiResponse.Success -> {
                emit(Resource.Success(mapType(apiResponse.data)))
            }
            is ApiResponse.Empty -> {
                emit(Resource.Success(loadEmpty()))
            }
            is ApiResponse.Error -> {
                onFetchFailed()
                emit(Resource.Error<ResultType>(apiResponse.errorMessage))
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract suspend fun createCall(): Flow<ApiResponse<RequestType>>

    protected abstract fun mapType(request: RequestType): ResultType

    protected abstract fun loadEmpty(): ResultType

    fun asFlow(): Flow<Resource<ResultType>> = result

}