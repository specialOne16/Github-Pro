package com.jundapp.githubpro.core.data

import com.jundapp.githubpro.core.data.source.local.LocalDataSource
import com.jundapp.githubpro.core.data.source.remote.RemoteDataSource
import com.jundapp.githubpro.core.data.source.remote.network.ApiResponse
import com.jundapp.githubpro.core.data.source.remote.response.DetailUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.repository.IUserRepository
import com.jundapp.githubpro.core.utils.AppExecutors
import com.jundapp.githubpro.core.utils.MappingHelper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class UserRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {

    override fun getAllUser(): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.getAllUser().map { MappingHelper.mapEntitiesToDomain(it) }
            }

            override fun shouldFetch(data: List<User>?): Boolean =
                data == null || data.isEmpty()

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getAllUser()

            override suspend fun saveCallResult(data: List<UserResponse>) {
                val userList = MappingHelper.mapResponsesToEntities(data)
                localDataSource.insertUser(userList)
            }
        }.asFlow()

    override fun searchUser(keyword: String): Flow<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun loadFromDB(): Flow<List<User>> {
                return localDataSource.searchUser(keyword).map {
                    MappingHelper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<User>?): Boolean =
                data == null || data.size < 10

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.searchUser(keyword)

            override suspend fun saveCallResult(data: List<UserResponse>) {
                val userList = MappingHelper.mapResponsesToEntities(data)
                localDataSource.insertUser(userList)
            }
        }.asFlow()

    override fun getUser(username: String): Flow<Resource<DetailUserData>> =
        object : NetworkOnlyResource<DetailUserData, DetailUserResponse>(appExecutors) {
            override suspend fun createCall(): Flow<ApiResponse<DetailUserResponse>> =
                remoteDataSource.getUser(username)

            override fun mapType(request: DetailUserResponse): DetailUserData =
                MappingHelper.mapDetailUserResponsesToDomain(request)

            override fun loadEmpty(): DetailUserData =
                DetailUserData("", 0, "", "", "", "")
        }.asFlow()

    override fun getFollowing(username: String): Flow<Resource<List<User>>> =
        object : NetworkOnlyResource<List<User>, List<UserResponse>>(appExecutors) {
            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowing(username)

            override fun mapType(request: List<UserResponse>): List<User> =
                MappingHelper.mapUserResponsesToDomain(request)

            override fun loadEmpty(): List<User> = arrayListOf()
        }.asFlow()

    override fun getFollower(username: String): Flow<Resource<List<User>>> =
        object : NetworkOnlyResource<List<User>, List<UserResponse>>(appExecutors) {
            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowers(username)

            override fun mapType(request: List<UserResponse>): List<User> =
                MappingHelper.mapUserResponsesToDomain(request)

            override fun loadEmpty(): List<User> = arrayListOf()
        }.asFlow()

    override fun getFavoriteUser(): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        emitAll(localDataSource.getFavoriteUser().map { MappingHelper.mapEntitiesToDomain(it) }
            .map { Resource.Success(it) })
    }

    override fun setFavorite(user: User, isFavorite: Boolean) {
        val userEntity = MappingHelper.mapDomainToEntity(user)
        appExecutors.diskIO().execute { localDataSource.setFavorite(userEntity, isFavorite) }
    }

    override fun getIsFavorite(user: User): Flow<Boolean> =
        localDataSource.getIsFavorite(MappingHelper.mapDomainToEntity(user))

}