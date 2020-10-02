package com.example.coinsliberty.di

import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import com.example.coinsliberty.utils.stub.StubViewModel

val viewModelModule = module {
    viewModel { StubViewModel() }
}