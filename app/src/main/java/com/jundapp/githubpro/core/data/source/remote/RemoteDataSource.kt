package com.jundapp.githubpro.core.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.jundapp.githubpro.core.data.source.remote.network.ApiResponse
import com.jundapp.githubpro.core.data.source.remote.network.ApiService
import com.jundapp.githubpro.core.data.source.remote.response.DetailUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(private val apiService: ApiService) {

    fun getAllUser(): LiveData<ApiResponse<List<UserResponse>>> {

        val resultData = MutableLiveData<ApiResponse<List<UserResponse>>>()

        val client = apiService.getList()

        client.enqueue(object : Callback<List<UserResponse>> {
            override fun onResponse(
                call: Call<List<UserResponse>>,
                response: Response<List<UserResponse>>
            ) {
                val dataArray = response.body()
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<List<UserResponse>>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData

    }

    fun getUser(username: String): LiveData<ApiResponse<DetailUserResponse>> {

        val resultData = MutableLiveData<ApiResponse<DetailUserResponse>>()

        val client = apiService.getUser(username)

        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                val dataArray = response.body()
                resultData.value =
                    if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData

    }

}