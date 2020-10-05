package com.example.coinsliberty.di

//import org.koin.android.viewmodel.ext.koin.viewModel
import com.example.coinsliberty.ui.dialogs.ChangeLanguageDialogViewModel
import org.koin.android.viewmodel.dsl.viewModel


//import org.koin.dsl.module.module
import org.koin.dsl.module



import com.example.coinsliberty.utils.stub.StubViewModel

val viewModelModule = module {
    viewModel { StubViewModel() }
    viewModel { ChangeLanguageDialogViewModel(get()) }
}