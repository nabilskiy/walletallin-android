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
): BaseViewModel(app) {

    val result = MutableLiveData<Boolean>()

    fun login(email: String, password: String, otp: String?) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val login = repository.login(LoginRequest(email, password, otp))
            if(login.result == false && otp.isNullOrEmpty()) {
                result.postValue(false)
            } else {
                handleResponse(login)
            }

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