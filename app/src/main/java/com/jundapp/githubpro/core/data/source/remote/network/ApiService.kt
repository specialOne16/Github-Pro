package com.jundapp.githubpro.core.data.source.remote.network

import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET

// TODO : perbaiki endpoint (sembunyikan access token)
interface ApiService {
    @GET("/users?access_token=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")
    fun getList(): Call<List<UserResponse>>
}