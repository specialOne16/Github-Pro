package com.jundapp.githubpro.core.domain.usecase

import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

// TODO : Injection - remove all userRepository usage except this
class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getAllUser(): Flow<Resource<List<User>>> = userRepository.getAllUser()
    override fun getUser(username: String): Flow<Resource<DetailUserData>> = userRepository.getUser(username)
}