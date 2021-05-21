package com.jundapp.githubpro.core.domain.usecase

import androidx.lifecycle.LiveData
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User

interface UserUseCase {
    fun getAllUser(): LiveData<Resource<List<User>>>
    fun getUser(username: String): LiveData<Resource<DetailUserData>>
}