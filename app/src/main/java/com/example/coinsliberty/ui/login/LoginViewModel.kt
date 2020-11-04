package com.example.coinsliberty.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.LoginRequest
import com.example.coinsliberty.data.SignUpRequest
import com.example.coinsliberty.data.SignUpResponse
import com.example.coinsliberty.model.SharedPreferencesProvider
import com.example.coinsliberty.ui.config.NavigationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: LoginRepository
): BaseViewModel(app) {

    val result = MutableLiveData<Boolean>()

    fun login(email: String, password: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.login(LoginRequest(email, password)))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }
    private fun handleResponse(signUp: SignUpResponse) {
        if(signUp.result == true) {
            sharedPreferencesProvider.setToken(signUp.token ?: "")
            Log.e("!!!", sharedPreferencesProvider.getToken() ?: "")
            result.postValue(true)
            return
        }

        showError.postValue(signUp.error?.message)
    }
}