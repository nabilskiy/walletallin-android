package com.coinsliberty.wallet.ui.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.LoginRequest
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val app: Application,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val repository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, repository) {

    val result = MutableLiveData<Boolean>()

    fun login(email: String, password: String, otp: String?) {
        launch(::handleError) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val login = repository.login(LoginRequest(email, password, otp))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
            Log.e("!!!", login.toString())
            if(login.result == false && otp.isNullOrEmpty()) {
                result.postValue(false)
            } else {
                sharedPreferencesProvider.saveLogin(email)
                sharedPreferencesProvider.savePassword(password)
                handleResponse(login)
            }


        }
    }
    private fun handleResponse(signUp: SignUpResponse) {
        if(signUp.result == true) {
            sharedPreferencesProvider.setToken(signUp.token ?: "")
            result.postValue(true)
            return
        }

        showError.postValue(signUp.error?.message ?: "Error")
    }

    private fun handleError(t: Throwable) {
        result.postValue(false)
    }


}