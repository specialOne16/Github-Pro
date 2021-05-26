package com.jundapp.githubpro.listuser

import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.repository.IUserRepository
import com.jundapp.githubpro.core.domain.usecase.UserInteractor
import com.jundapp.githubpro.core.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserListViewModelTest {

    private lateinit var userUseCase: UserUseCase
    private val dummyUser = listOf(User("specialOne16", 123456, "https://avatar_url"))

    @Mock
    private lateinit var userRepository: IUserRepository

    @Before
    fun setUp(){
        userUseCase = UserInteractor(userRepository)

        `when`(userRepository.getAllUser()).thenReturn(flow { emit(Resource.Success(dummyUser)) })
        `when`(userRepository.searchUser(anyString())).thenReturn(flow { emit(Resource.Success(dummyUser)) })
        `when`(userRepository.getFavoriteUser()).thenReturn(flow { emit(Resource.Success(dummyUser)) })
        `when`(userRepository.getFollower(anyString())).thenReturn(flow { emit(Resource.Success(dummyUser)) })
        `when`(userRepository.getFollowing(anyString())).thenReturn(flow { emit(Resource.Success(dummyUser)) })
    }

    @Test
    fun getUsers() = runBlocking{

        val firstItem = userUseCase.getAllUser().first()

        verify(userRepository).getAllUser()

        assertNotNull(firstItem.data)
        assertEquals(firstItem.data?.size, dummyUser.size)

    }

    @Test
    fun searchUser() = runBlocking{

        val firstItem = userUseCase.searchUser("specialOne16").first()

        verify(userRepository).searchUser("specialOne16")

        assertNotNull(firstItem.data)
        assertEquals(firstItem.data?.size, dummyUser.size)

    }

    @Test
    fun getFavorites() = runBlocking{

        val firstItem = userUseCase.getFavoriteUser().first()

        verify(userRepository).getFavoriteUser()

        assertNotNull(firstItem.data)
        assertEquals(firstItem.data?.size, dummyUser.size)

    }

    @Test
    fun getFollower() = runBlocking{

        val firstItem = userUseCase.getFollower("specialOne16").first()

        verify(userRepository).getFollower("specialOne16")

        assertNotNull(firstItem.data)
        assertEquals(firstItem.data?.size, dummyUser.size)

    }

    @Test
    fun getFollowing() = runBlocking{

        val firstItem = userUseCase.getFollowing("specialOne16").first()

        verify(userRepository).getFollowing("specialOne16")

        assertNotNull(firstItem.data)
        assertEquals(firstItem.data?.size, dummyUser.size)

    }
}