package com.jundapp.githubpro.core.domain.usecase

import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserUseCase {
    fun getAllUser(): Flow<Resource<List<User>>>
    fun searchUser(keyword: String): Flow<Resource<List<User>>>
    fun getUser(username: String): Flow<Resource<DetailUserData>>

    fun getFollowing(username: String): Flow<Resource<List<User>>>
    fun getFollower(username: String): Flow<Resource<List<User>>>

    fun getFavoriteUser(): Flow<Resource<List<User>>>
    fun setFavorite(user: User, isFavorite: Boolean)
    fun getIsFavorite(user: User): Flow<Boolean>
}