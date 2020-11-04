package com.example.coinsliberty.dialogs

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.ChangePasswordRequest
import com.example.coinsliberty.data.SignUpRequest
import com.example.coinsliberty.data.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class ResetPassViewModel(
    app: Application,
    private val repository: ResetPassRepository
): BaseViewModel(app) {

    val timeToDismiss : MutableLiveData<Boolean> = MutableLiveData()

    fun changePassword(password: String, oldPassword: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.changePassword(ChangePasswordRequest(password, oldPassword)))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
            //timeToDismiss.postValue(true)
        }
    }

    private fun handleResponse(response: SignUpResponse) {
        if(response.result == true) {
            timeToDismiss.postValue(true)
            return
        }

        showError.postValue(response.error?.message)
    }

}