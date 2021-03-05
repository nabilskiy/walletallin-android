package com.coinsliberty.wallet.ui.signup

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.SignUpRequest
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class SignUpViewModel(
    app: Application,
    private val repository: SignUpRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val result = MutableLiveData<Boolean>()

    var signUpJob: Job? = null

    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        signUpJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.signUp(SignUpRequest(email, password, firstName, lastName)))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    override fun stopRequest() {
        signUpJob?.cancel()
    }

    fun showError(error: String) {
        showError.postValue(error)
    }

    private fun handleResponse(signUp: SignUpResponse) {
        if(signUp.result == true) {
            result.postValue(true)
            return
        }

        showError.postValue(signUp.error?.message ?: "Error")
    }

}