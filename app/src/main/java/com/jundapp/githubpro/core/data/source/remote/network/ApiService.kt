package com.jundapp.githubpro.core.data.source.remote.network

import com.jundapp.githubpro.core.data.source.remote.response.DetailUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.SearchUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    fun getList(): Call<List<UserResponse>>

    @GET("/search/users")
    fun searchUser(@Query("q") keyword: String): Call<SearchUserResponse>

    @GET("/users/{username}")
    fun getUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("/users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserResponse>>

    @GET("/users/{username}/followers")
    fun getFollower(@Path("username") username: String): Call<List<UserResponse>>

}