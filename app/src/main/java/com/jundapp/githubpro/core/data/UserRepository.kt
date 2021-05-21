package com.jundapp.githubpro.core.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.jundapp.githubpro.core.data.source.local.LocalDataSource
import com.jundapp.githubpro.core.data.source.remote.RemoteDataSource
import com.jundapp.githubpro.core.data.source.remote.network.ApiResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
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

    override fun getAllUser(keyword: String): LiveData<Resource<List<User>>> {
        // TODO("Not yet implemented")
        return MutableLiveData()
    }

    override fun getAllFavoriteUser(): LiveData<List<User>> {
        // TODO : change to favorite user
        return Transformations.map(localDataSource.getAllUser()) {
            MappingHelper.mapEntitiesToDomain(it)
        }
    }

    override fun setFavoriteUser(user: User, state: Boolean) {
        val userEntity = MappingHelper.mapDomainToEntity(user)
        // TODO : set favorite user
//        appExecutors.diskIO().execute { localDataSource.setFavoriteTourism(userEntity, state) }
    }

}