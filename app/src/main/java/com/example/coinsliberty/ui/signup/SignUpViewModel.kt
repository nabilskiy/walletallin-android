package com.example.coinsliberty.ui.signup

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.SignUpRequest
import com.example.coinsliberty.ui.config.NavigationConfig

class SignUpViewModel(
    app: Application,
    private val repository: SignUpRepository
): BaseViewModel(app) {

    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        launch(::onErrorHandler) {
            val response = repository.signUp(SignUpRequest(email, password, firstName, lastName))
            Log.e("!!!", response.toString())
        }
    }

}