package com.coinsliberty.wallet.dialogs.forgetPassword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.ForgotPassRequest
import com.coinsliberty.wallet.data.response.SignUpResponse
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