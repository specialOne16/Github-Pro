package com.jundapp.githubpro.core.domain.repository

import androidx.lifecycle.LiveData
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.User

interface IUserRepository {
    fun getAllUser(): LiveData<Resource<List<User>>>
    fun getAllUser(keyword: String): LiveData<Resource<List<User>>>
    fun getAllFavoriteUser(): LiveData<List<User>>
    fun setFavoriteUser(user: User, state: Boolean)
}