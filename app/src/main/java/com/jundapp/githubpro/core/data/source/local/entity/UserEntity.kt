package com.jundapp.githubpro.core.data.source.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "user")
data class UserEntity (
    @ColumnInfo(name = "login")
    val username: String,

    @PrimaryKey
    @NonNull
    val id: Int,

    val avatar_url: String?,
) : Parcelable