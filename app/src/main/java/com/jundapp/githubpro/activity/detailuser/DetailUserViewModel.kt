package com.jundapp.githubpro.activity.detailuser

import androidx.lifecycle.ViewModel
import com.jundapp.githubpro.core.domain.repository.IUserRepository

class DetailUserViewModel(userRepository: IUserRepository) : ViewModel() {
    val users = userRepository.getAllUser()
}