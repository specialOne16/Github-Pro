package com.jundapp.githubpro.di

import com.jundapp.githubpro.detailuser.DetailUserViewModel
import com.jundapp.githubpro.core.domain.usecase.UserInteractor
import com.jundapp.githubpro.core.domain.usecase.UserUseCase
import com.jundapp.githubpro.core.ui.UserListAdapter
import com.jundapp.githubpro.listuser.UserListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {
    val useCaseModule = module {
        factory<UserUseCase> { UserInteractor(get()) }
    }

    val viewModelModule = module {
        viewModel { UserListViewModel(get()) }
        viewModel { DetailUserViewModel(get()) }
    }

    val adapterModule = module {
        factory { UserListAdapter(get()) }
    }
}