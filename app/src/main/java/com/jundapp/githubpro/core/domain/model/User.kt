package com.jundapp.githubpro.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val username: String,
    val id: Int,
    val avatar_url: String?,
) : Parcelable
