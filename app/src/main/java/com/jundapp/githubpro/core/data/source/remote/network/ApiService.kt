package com.jundapp.githubpro.core.data.source.remote.network

import com.jundapp.githubpro.core.data.source.remote.response.DetailUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/users")
    fun getList(): Call<List<UserResponse>>

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Call<DetailUserResponse>
}