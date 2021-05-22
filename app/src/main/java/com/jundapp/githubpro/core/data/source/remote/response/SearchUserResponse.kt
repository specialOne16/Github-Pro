package com.jundapp.githubpro.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val isComplete: Boolean,

    @field:SerializedName("items")
    val users: List<UserResponse>

)
