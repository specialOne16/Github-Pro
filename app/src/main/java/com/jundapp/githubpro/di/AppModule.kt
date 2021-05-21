package com.jundapp.githubpro.di

import com.jundapp.githubpro.core.domain.usecase.UserInteractor
import com.jundapp.githubpro.core.domain.usecase.UserUseCase
import com.jundapp.githubpro.core.ui.UserListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val useCaseModule = module {
        factory<UserUseCase> { UserInteractor(get()) }
    }

    // TODO : add view model
    val viewModelModule = module {
        viewModel { UserListViewModel(get()) }
//        viewModel { HomeViewModel(get()) }
//        viewModel { FavoriteViewModel(get()) }
//        viewModel { DetailTourismViewModel(get()) }
    }
}