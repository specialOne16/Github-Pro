package com.jundapp.githubpro.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class DetailUserResponse(

    @field:SerializedName("login")
    val username: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar_url")
    val avatar_url: String?,

    @field:SerializedName("name")
    val name: String?,

    @field:SerializedName("location")
    val location: String?,

    @field:SerializedName("company")
    val company: String?

)
