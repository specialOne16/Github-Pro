package com.jundapp.githubpro.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatar_url: String?,

)
