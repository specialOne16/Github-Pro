package com.jundapp.githubpro.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jundapp.githubpro.core.data.source.local.LocalDataSource
import com.jundapp.githubpro.core.data.source.local.entity.UserEntity
import com.jundapp.githubpro.core.data.source.remote.RemoteDataSource
import com.jundapp.githubpro.core.data.source.remote.network.ApiResponse
import com.jundapp.githubpro.core.data.source.remote.response.DetailUserResponse
import com.jundapp.githubpro.core.data.source.remote.response.UserResponse
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.utils.AppExecutors
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val dummyUser =
        listOf(
            User("specialOne16", 123456, "https://avatar_url"),
            User("specialOne16", 123457, "https://avatar_url"),
            User("specialOne16", 123458, "https://avatar_url"),
            User("specialOne16", 123459, "https://avatar_url"),
            User("specialOne16", 123450, "https://avatar_url"),
            User("specialOne16", 123451, "https://avatar_url"),
            User("specialOne16", 123452, "https://avatar_url"),
            User("specialOne16", 123453, "https://avatar_url"),
            User("specialOne16", 123454, "https://avatar_url"),
            User("specialOne16", 123455, "https://avatar_url")
        )
    private val dummyUserEntity =
        listOf(
            UserEntity("specialOne16", 123456, "https://avatar_url", true),
            UserEntity("specialOne16", 123457, "https://avatar_url", true),
            UserEntity("specialOne16", 123458, "https://avatar_url", true),
            UserEntity("specialOne16", 123459, "https://avatar_url", true),
            UserEntity("specialOne16", 123450, "https://avatar_url", true),
            UserEntity("specialOne16", 123451, "https://avatar_url", true),
            UserEntity("specialOne16", 123452, "https://avatar_url", true),
            UserEntity("specialOne16", 123453, "https://avatar_url", true),
            UserEntity("specialOne16", 123454, "https://avatar_url", true),
            UserEntity("specialOne16", 123455, "https://avatar_url", true)
        )
    private val dummyUserResponse =
        listOf(
            UserResponse("specialOne16", 123456, "https://avatar_url"),
            UserResponse("specialOne16", 123457, "https://avatar_url"),
            UserResponse("specialOne16", 123458, "https://avatar_url"),
            UserResponse("specialOne16", 123459, "https://avatar_url"),
            UserResponse("specialOne16", 123450, "https://avatar_url"),
            UserResponse("specialOne16", 123451, "https://avatar_url"),
            UserResponse("specialOne16", 123452, "https://avatar_url"),
            UserResponse("specialOne16", 123453, "https://avatar_url"),
            UserResponse("specialOne16", 123454, "https://avatar_url"),
            UserResponse("specialOne16", 123455, "https://avatar_url")
        )
    private val dummyDetailUser =
        DetailUserData("specialOne16", 123456, "https://avatar_url", "Jundu", null, null)
    private val dummyDetailUserResponse =
        DetailUserResponse("specialOne16", 123456, "https://avatar_url", "Jundu", null, null)

    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var localDataSource: LocalDataSource

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    private val executors: AppExecutors = AppExecutors()

    @Before
    fun setUp() {
        userRepository = UserRepository(remoteDataSource, localDataSource, executors)
    }

    @Test
    fun getAllUser() = runBlocking {

        `when`(localDataSource.getAllUser()).thenReturn(flow { emit(dummyUserEntity) })

        lateinit var result: Resource.Success<List<User>>
        userRepository.getAllUser().collect { value ->
            if (value is Resource.Success) {
                result = value
            }
        }

        verify(localDataSource, times(2)).getAllUser()

        assertNotNull(result.data)
        assertEquals(result.data?.size, dummyUser.size)
        assertEquals(result.data?.get(0), dummyUser[0])

    }

    @Test
    fun searchUser() = runBlocking {

        `when`(localDataSource.searchUser(anyString())).thenReturn(flow { emit(dummyUserEntity) })

        lateinit var result: Resource.Success<List<User>>
        userRepository.searchUser("specialOne16").collect { value ->
            if (value is Resource.Success) {
                result = value
            }
        }

        verify(localDataSource, times(2)).searchUser("specialOne16")

        assertNotNull(result.data)
        assertEquals(result.data?.size, dummyUser.size)
        assertEquals(result.data?.get(0), dummyUser[0])

    }

    @Test
    fun getUser() = runBlocking {

        `when`(remoteDataSource.getUser(anyString())).thenReturn(flow {
            emit(
                ApiResponse.Success(
                    dummyDetailUserResponse
                )
            )
        })

        lateinit var result: Resource.Success<DetailUserData>
        userRepository.getUser("specialOne16").collect { value ->
            if (value is Resource.Success) {
                result = value
            }
        }

        verify(remoteDataSource).getUser("specialOne16")

        assertNotNull(result.data)
        assertEquals(result.data, dummyDetailUser)

    }

    @Test
    fun getFollowing() = runBlocking {

        `when`(remoteDataSource.getFollowing(anyString())).thenReturn(flow {
            emit(
                ApiResponse.Success(
                    dummyUserResponse
                )
            )
        })

        lateinit var result: Resource.Success<List<User>>
        userRepository.getFollowing("specialOne16").collect { value ->
            if (value is Resource.Success) {
                result = value
            }
        }

        verify(remoteDataSource).getFollowing("specialOne16")

        assertNotNull(result.data)
        assertEquals(result.data, dummyUser)

    }

    @Test
    fun getFollower() = runBlocking {

        `when`(remoteDataSource.getFollowers(anyString())).thenReturn(flow {
            emit(
                ApiResponse.Success(
                    dummyUserResponse
                )
            )
        })

        lateinit var result: Resource.Success<List<User>>
        userRepository.getFollower("specialOne16").collect { value ->
            if (value is Resource.Success) {
                result = value
            }
        }

        verify(remoteDataSource).getFollowers("specialOne16")

        assertNotNull(result.data)
        assertEquals(result.data, dummyUser)

    }

    @Test
    fun getFavoriteUser() = runBlocking {

        `when`(localDataSource.getFavoriteUser()).thenReturn(flow { emit(dummyUserEntity) })

        lateinit var result: Resource.Success<List<User>>
        userRepository.getFavoriteUser().collect { value ->
            if (value is Resource.Success) {
                result = value
            }
        }

        verify(localDataSource).getFavoriteUser()

        assertNotNull(result.data)
        assertEquals(result.data?.size, dummyUser.size)
        assertEquals(result.data?.get(0), dummyUser[0])

    }

    @Test
    fun setFavorite() {

        val dummySingleUser = User("specialOne16",123456,"https://avatar_url",)
        val dummySingleUserEntity = UserEntity("specialOne16",123456,"https://avatar_url",false)

        userRepository.setFavorite(dummySingleUser, true)
        Thread.sleep(1000)
        verify(localDataSource).setFavorite(dummySingleUserEntity, true)

    }

    @Test
    fun getIsFavorite() = runBlocking {

        val dummySingleUser = User("specialOne16",123456,"https://avatar_url",)
        val dummySingleUserEntity = UserEntity("specialOne16",123456,"https://avatar_url",false)

        `when`(localDataSource.getIsFavorite(dummySingleUserEntity)).thenReturn(flow { emit(false) })

        val isFav = userRepository.getIsFavorite(dummySingleUser).first()

        verify(localDataSource).getIsFavorite(dummySingleUserEntity)

        assertFalse(isFav)
    }
}