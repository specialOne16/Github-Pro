package com.jundapp.githubpro.activity.detailuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.repository.IUserRepository

class DetailUserViewModel(private val userRepository: IUserRepository) : ViewModel() {
    fun getUserDetail(username: String): LiveData<Resource<DetailUserData>> =
        userRepository.getUser(username).asLiveData()

    fun setFavorite(user: User, isFavorite: Boolean) = userRepository.setFavorite(user, isFavorite)
    fun getIsFavorite(user: User) = userRepository.getIsFavorite(user).asLiveData()
}