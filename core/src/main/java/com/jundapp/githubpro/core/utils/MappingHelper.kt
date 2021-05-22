package com.jundapp.githubpro.core.utils

import com.jundapp.githubpro.core.data.source.local.entity.UserEntity
import com.jundapp.githubpro.core.data.source.remote.response.DetailUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User

object MappingHelper {

    fun mapEntitiesToDomain(data: List<UserEntity>): List<User> =
        data.map {
            User(
                username = it.username,
                id = it.id,
                avatarUrl = it.avatarUrl
            )
        }

    fun mapDomainToEntity(data: User): UserEntity = UserEntity(
        username = data.username,
        id = data.id,
        avatarUrl = data.avatarUrl
    )

    fun mapResponsesToEntities(data: List<UserResponse>): List<UserEntity> {
        val userList = ArrayList<UserEntity>()
        data.map {
            val user = UserEntity(
                username = it.username,
                id = it.id,
                avatarUrl = it.avatar_url
            )
            userList.add(user)
        }
        return userList
    }

    fun mapDetailUserResponsesToDomain(data: DetailUserResponse): DetailUserData = DetailUserData(
        username = data.username,
        id = data.id,
        avatar_url = data.avatar_url,
        name = data.name,
        location = data.location,
        company = data.company
    )

    fun mapUserResponsesToDomain(data: List<UserResponse>): List<User> =
        data.map {
            User(
                username = it.username,
                id = it.id,
                avatarUrl = it.avatar_url
            )
        }
}