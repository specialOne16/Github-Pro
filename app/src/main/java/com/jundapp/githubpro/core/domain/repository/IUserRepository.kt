package com.jundapp.githubpro.core.domain.repository

import androidx.lifecycle.LiveData
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User

interface IUserRepository {
    fun getAllUser(): LiveData<Resource<List<User>>>
    fun searchUser(keyword: String): LiveData<Resource<List<User>>>
    fun getUser(username: String): LiveData<Resource<DetailUserData>>

    fun getFollowing(username: String): LiveData<Resource<List<User>>>
    fun getFollower(username: String): LiveData<Resource<List<User>>>

    fun getFavoriteUser(): LiveData<Resource<List<User>>>
    fun setFavorite(user: User, isFavorite: Boolean)
    fun getIsFavorite(user: User): LiveData<Boolean>
}