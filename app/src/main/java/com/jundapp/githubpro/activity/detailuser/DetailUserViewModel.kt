package com.jundapp.githubpro.activity.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.repository.IUserRepository

class DetailUserViewModel(val userRepository: IUserRepository) : ViewModel() {
    fun getUserDetail(username: String): LiveData<Resource<DetailUserData>> = userRepository.getUser(username)
}