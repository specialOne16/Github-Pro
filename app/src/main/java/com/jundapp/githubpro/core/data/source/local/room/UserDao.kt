package com.jundapp.githubpro.core.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jundapp.githubpro.core.data.source.local.entity.UserEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser(): LiveData<List<UserEntity>>

    // TODO : Get favorite user

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: List<UserEntity>)

    @Update
    fun updateUser(user: UserEntity)

}