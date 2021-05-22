package com.jundapp.githubpro.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jundapp.githubpro.core.domain.usecase.UserUseCase

class UserListViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    val users = userUseCase.getAllUser().asLiveData()
    fun searchUser(keyword: String) = userUseCase.searchUser(keyword).asLiveData()
    fun getFavorites() = userUseCase.getFavoriteUser().asLiveData()

    fun getFollower(username: String) = userUseCase.getFollower(username).asLiveData()
    fun getFollowing(username: String) = userUseCase.getFollowing(username).asLiveData()
}