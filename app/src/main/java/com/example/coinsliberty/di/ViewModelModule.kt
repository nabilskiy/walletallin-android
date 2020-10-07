
package com.example.coinsliberty.di
import com.example.coinsliberty.utils.stub.StubViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StubViewModel() }
}