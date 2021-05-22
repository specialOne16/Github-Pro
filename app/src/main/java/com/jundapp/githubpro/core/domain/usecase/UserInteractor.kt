package com.jundapp.githubpro.core.domain.usecase

import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository) : UserUseCase {
    override fun getAllUser(): Flow<Resource<List<User>>> = userRepository.getAllUser()
    override fun searchUser(keyword: String): Flow<Resource<List<User>>> = userRepository.searchUser(keyword)
    override fun getUser(username: String): Flow<Resource<DetailUserData>> = userRepository.getUser(username)

    override fun getFollowing(username: String): Flow<Resource<List<User>>> = userRepository.getFollowing(username)
    override fun getFollower(username: String): Flow<Resource<List<User>>> = userRepository.getFollower(username)
    override fun getFavoriteUser(): Flow<Resource<List<User>>> = userRepository.getFavoriteUser()

    override fun setFavorite(user: User, isFavorite: Boolean) = userRepository.setFavorite(user, isFavorite)
    override fun getIsFavorite(user: User): Flow<Boolean> = userRepository.getIsFavorite(user)
}