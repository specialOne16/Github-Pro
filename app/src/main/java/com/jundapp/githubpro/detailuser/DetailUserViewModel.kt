package com.jundapp.githubpro.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.usecase.UserUseCase

class DetailUserViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun getUserDetail(username: String): LiveData<Resource<DetailUserData>> =
        userUseCase.getUser(username).asLiveData()

    fun setFavorite(user: User, isFavorite: Boolean) = userUseCase.setFavorite(user, isFavorite)
    fun getIsFavorite(user: User) = userUseCase.getIsFavorite(user).asLiveData()
}