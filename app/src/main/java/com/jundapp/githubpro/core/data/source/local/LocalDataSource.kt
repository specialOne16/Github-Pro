package com.jundapp.githubpro.core.data.source.local

import com.jundapp.githubpro.core.data.source.local.entity.UserEntity
import com.jundapp.githubpro.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {

    fun getAllUser(): Flow<List<UserEntity>> = userDao.getAllUser()
    fun searchUser(keyword: String): Flow<List<UserEntity>> = userDao.searchUser("%$keyword%")

    suspend fun insertUser(users: List<UserEntity>) = userDao.insertUser(users)

    fun getFavoriteUser(): Flow<List<UserEntity>> = userDao.getFavoriteUser()

    fun setFavorite(user: UserEntity, isFavorite: Boolean){
        user.isFavorite = isFavorite
        userDao.updateUser(user)
    }

    fun getIsFavorite(user: UserEntity): Flow<Boolean> = userDao.getIsFavorite(user.id)

}