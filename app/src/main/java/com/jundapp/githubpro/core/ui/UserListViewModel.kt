package com.jundapp.githubpro.core.ui

import androidx.lifecycle.ViewModel
import com.jundapp.githubpro.core.domain.repository.IUserRepository

class UserListViewModel(userRepository: IUserRepository) : ViewModel() {
    val users = userRepository.getAllUser()
}