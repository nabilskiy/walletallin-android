package com.example.coinsliberty.dialogs

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.BtcBalance
import com.example.coinsliberty.data.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody

class SendBtcViewModel (
    private val app: Application,
    private val repository: BtcRepository
): BaseViewModel(app) {

    val result = MutableLiveData<Boolean>()

    fun sendBtc(asset: String, amount: String, address: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.sendBtcBalance(BtcBalance(asset, amount, address)))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(signUp: SignUpResponse) {
        if(signUp.result == true) {
            result.postValue(true)
            return
        }

        showError.postValue(signUp.error?.message)
    }
}