package com.jundapp.githubpro.core.domain.usecase

import androidx.lifecycle.LiveData
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.repository.IUserRepository

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getAllUser(): LiveData<Resource<List<User>>> = userRepository.getAllUser()
    override fun getUser(username: String): LiveData<Resource<DetailUserData>> = userRepository.getUser(username)
}