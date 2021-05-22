package com.jundapp.githubpro.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailUserData(
    val username: String,
    val id: Int,
    val avatar_url: String?,
    val name: String?,
    val location: String?,
    val company: String?
) : Parcelable