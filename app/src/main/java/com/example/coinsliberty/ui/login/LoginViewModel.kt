package com.example.coinsliberty.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.LoginRequest
import com.example.coinsliberty.data.SignUpRequest
import com.example.coinsliberty.ui.config.NavigationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val app: Application,
    private val repository: LoginRepository
): BaseViewModel(app) {

    fun login(email: String, password: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.login(LoginRequest(email, password))
            Log.e("!!!", response.toString())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

}