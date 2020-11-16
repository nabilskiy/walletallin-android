package com.coinsliberty.wallet.dialogs.sendDialog

import android.app.Application
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendBtcViewModel (
    private val app: Application,
    private val repository: BtcRepository
): BaseViewModel(app) {

    val result = MutableLiveData<Boolean>()

    fun sendBtc(asset: String, amount: String, address: String, otp: Editable) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.sendBtcBalance(BtcBalance(asset, amount, address, otp.toString())))
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