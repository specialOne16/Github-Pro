package com.jundapp.githubpro.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jundapp.githubpro.core.data.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE username LIKE :keyword")
    fun searchUser(keyword: String): LiveData<List<UserEntity>>

    @Query("SELECT * FROM user WHERE isFavorite=1")
    fun getFavoriteUser(): LiveData<List<UserEntity>>

    @Query("SELECT isFavorite FROM user WHERE id=:userId")
    fun getIsFavorite(userId: Int): LiveData<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: List<UserEntity>)

    @Update
    fun updateUser(user: UserEntity)

}