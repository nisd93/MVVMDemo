package com.mvvmdemo.di

import com.mvvmdemo.repository.MerchantListRepository
import com.mvvmdemo.view.viewmodel.MerchantListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MerchantListViewModel(get())
    }

    single{
        MerchantListRepository(get())
    }

}