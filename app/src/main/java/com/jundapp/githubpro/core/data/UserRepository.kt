package com.jundapp.githubpro.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
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

class UserRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : IUserRepository {

    override fun getAllUser(): LiveData<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<User>> {
                return Transformations.map(localDataSource.getAllUser()) {
                    MappingHelper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<User>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getAllUser()

            override fun saveCallResult(data: List<UserResponse>) {
                val userList = MappingHelper.mapResponsesToEntities(data)
                localDataSource.insertUser(userList)
            }
        }.asLiveData()

    override fun searchUser(keyword: String): LiveData<Resource<List<User>>> =
        object : NetworkBoundResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun loadFromDB(): LiveData<List<User>> {
                return Transformations.map(localDataSource.searchUser(keyword)) {
                    MappingHelper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<User>?): Boolean =
                data == null || data.size < 10

            override fun createCall(): LiveData<ApiResponse<List<UserResponse>>> =
                remoteDataSource.searchUser(keyword)

            override fun saveCallResult(data: List<UserResponse>) {
                val userList = MappingHelper.mapResponsesToEntities(data)
                localDataSource.insertUser(userList)
            }
        }.asLiveData()

    override fun getUser(username: String): LiveData<Resource<DetailUserData>> =
        object : NetworkOnlyResource<DetailUserData, DetailUserResponse>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<DetailUserResponse>> =
                remoteDataSource.getUser(username)

            override fun mapType(request: DetailUserResponse): DetailUserData =
                MappingHelper.mapDetailUserResponsesToDomain(request)

            override fun loadEmpty(): DetailUserData =
                DetailUserData("", 0, "", "", "", "")
        }.asLiveData()

    override fun getFollowing(username: String): LiveData<Resource<List<User>>> =
        object : NetworkOnlyResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowing(username)

            override fun mapType(request: List<UserResponse>): List<User> =
                MappingHelper.mapUserResponsesToDomain(request)

            override fun loadEmpty(): List<User> = arrayListOf()
        }.asLiveData()

    override fun getFollower(username: String): LiveData<Resource<List<User>>> =
        object : NetworkOnlyResource<List<User>, List<UserResponse>>(appExecutors) {
            override fun createCall(): LiveData<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowers(username)

            override fun mapType(request: List<UserResponse>): List<User> =
                MappingHelper.mapUserResponsesToDomain(request)

            override fun loadEmpty(): List<User> = arrayListOf()
        }.asLiveData()

    override fun getFavoriteUser(): LiveData<Resource<List<User>>> {
        val result = MediatorLiveData<Resource<List<User>>>()
        result.addSource(Transformations.map(localDataSource.getFavoriteUser()) {
            MappingHelper.mapEntitiesToDomain(it)
        }) { favorites ->
            result.value = Resource.Success(favorites)
        }
        return result
    }

    override fun setFavorite(user: User, isFavorite: Boolean) {
        val userEntity = MappingHelper.mapDomainToEntity(user)
        appExecutors.diskIO().execute { localDataSource.setFavorite(userEntity, isFavorite) }
    }

    override fun getIsFavorite(user: User): LiveData<Boolean> = localDataSource.getIsFavorite(MappingHelper.mapDomainToEntity(user))

}