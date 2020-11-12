package com.example.coinsliberty.dialogs.forgetPassword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.ForgotPassRequest
import com.example.coinsliberty.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForgotPassViewModel(
    app: Application,
    private val repository: ForgotPassRepository
) : BaseViewModel(app) {

    val resultRecovery = MutableLiveData<SignUpResponse>()

    fun forgotPass(email: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.forgotPass(ForgotPassRequest(email)))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(signUp: SignUpResponse) {
        if(signUp.result == true) {
            resultRecovery.postValue(signUp)
            return
        }

        showError.postValue(signUp.error?.message)
    }

}