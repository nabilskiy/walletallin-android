package com.example.coinsliberty.dialogs

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.ForgotPassRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ForgotPassViewModel(
    app: Application,
    private val repository: ForgotPassRepository
) : BaseViewModel(app) {

    val resultRecovery = MutableLiveData<Boolean>()
    val messageRecovery = MutableLiveData<String>()

    fun forgotPass(email: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val response = repository.forgotPass(ForgotPassRequest(email))
            Log.e("!!!", response.toString())
            resultRecovery.postValue(response.result)
            messageRecovery.postValue(response.error?.message)
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

}