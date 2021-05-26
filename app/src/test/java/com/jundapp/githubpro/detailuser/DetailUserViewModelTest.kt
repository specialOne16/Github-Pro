package com.jundapp.githubpro.detailuser

import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.DetailUserData
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.core.domain.repository.IUserRepository
import com.jundapp.githubpro.core.domain.usecase.UserInteractor
import com.jundapp.githubpro.core.domain.usecase.UserUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailUserViewModelTest {

    private lateinit var userUseCase: UserUseCase
    private val dummyUser = listOf(User("specialOne16", 123456, "https://avatar_url"))
    private val dummyDetailUser = DetailUserData("specialOne16", 123456, "https://avatar_url","Jundu",null,null)

    @Mock
    private lateinit var userRepository: IUserRepository

    @Before
    fun setUp(){
        userUseCase = UserInteractor(userRepository)

        `when`(userRepository.getUser(anyString())).thenReturn(flow { emit(Resource.Success(dummyDetailUser)) })
        `when`(userRepository.getIsFavorite(dummyUser[0])).thenReturn(flow { emit(true) })
    }

    @Test
    fun getUserDetail() = runBlocking{

        val firstItem = userUseCase.getUser("specialOne16").first()

        verify(userRepository).getUser("specialOne16")

        assertNotNull(firstItem.data)
        assertEquals(firstItem.data?.name, dummyDetailUser.name)

    }

    @Test
    fun setFavorite() {
        userUseCase.setFavorite(dummyUser[0], true)
        verify(userRepository).setFavorite(dummyUser[0], true)
    }

    @Test
    fun getIsFavorite() = runBlocking{
        val isFav = userUseCase.getIsFavorite(dummyUser[0]).first()

        verify(userRepository).getIsFavorite(dummyUser[0])

        assertTrue(isFav)
    }
}