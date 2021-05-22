package com.jundapp.githubpro.core.data.source.remote.network

import com.jundapp.githubpro.core.data.source.remote.response.DetailUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.SearchUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    suspend fun getList(): List<UserResponse>

    @GET("/search/users")
    suspend fun searchUser(@Query("q") keyword: String): SearchUserResponse

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") username: String): DetailUserResponse

    @GET("/users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String): List<UserResponse>

    @GET("/users/{username}/followers")
    suspend fun getFollower(@Path("username") username: String): List<UserResponse>

}