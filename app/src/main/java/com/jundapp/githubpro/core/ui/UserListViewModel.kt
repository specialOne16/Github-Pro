package com.jundapp.githubpro.core.ui

import androidx.lifecycle.ViewModel
import com.jundapp.githubpro.core.domain.repository.IUserRepository

class UserListViewModel(val userRepository: IUserRepository) : ViewModel() {
    val users = userRepository.getAllUser()
    fun searchUser(keyword: String) = userRepository.searchUser(keyword)
    fun getFollower(username: String) = userRepository.getFollower(username)
    fun getFollowing(username: String) = userRepository.getFollowing(username)
}