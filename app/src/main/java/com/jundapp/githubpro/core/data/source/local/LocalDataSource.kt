package com.jundapp.githubpro.core.data.source.local

import androidx.lifecycle.LiveData
import com.jundapp.githubpro.core.data.source.local.entity.UserEntity
import com.jundapp.githubpro.core.data.source.local.room.UserDao

class LocalDataSource(private val userDao: UserDao) {

    fun getAllUser(): LiveData<List<UserEntity>> = userDao.getAllUser()
    fun searchUser(keyword: String): LiveData<List<UserEntity>> = userDao.searchUser("%$keyword%")

    // TODO : get favorite user

    fun insertUser(users: List<UserEntity>) = userDao.insertUser(users)

    // TODO : Set favorite user

}