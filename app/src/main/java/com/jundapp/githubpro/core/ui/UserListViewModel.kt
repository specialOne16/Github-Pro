package com.jundapp.githubpro.core.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jundapp.githubpro.core.domain.repository.IUserRepository

class UserListViewModel(private val userRepository: IUserRepository) : ViewModel() {
    val users = userRepository.getAllUser().asLiveData()
    fun searchUser(keyword: String) = userRepository.searchUser(keyword).asLiveData()
    fun getFavorites() = userRepository.getFavoriteUser().asLiveData()

    fun getFollower(username: String) = userRepository.getFollower(username).asLiveData()
    fun getFollowing(username: String) = userRepository.getFollowing(username).asLiveData()
}