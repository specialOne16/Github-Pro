package com.jundapp.githubpro.core.data.source.local.room

import androidx.room.*
import com.jundapp.githubpro.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAllUser(): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE username LIKE :keyword")
    fun searchUser(keyword: String): Flow<List<UserEntity>>

    @Query("SELECT * FROM user WHERE isFavorite=1")
    fun getFavoriteUser(): Flow<List<UserEntity>>

    @Query("SELECT isFavorite FROM user WHERE id=:userId")
    fun getIsFavorite(userId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: List<UserEntity>)

    @Update
    fun updateUser(user: UserEntity)

}