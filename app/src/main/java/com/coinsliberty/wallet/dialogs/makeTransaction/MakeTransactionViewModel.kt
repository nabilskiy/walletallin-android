package com.coinsliberty.wallet.dialogs.makeTransaction

import android.app.Application
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.EditProfileRequest
import com.coinsliberty.wallet.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MakeTransactionViewModel (
    private val app: Application,
    private val repository: MakeTransactionRepository
): BaseViewModel(app) {

    val result = MutableLiveData<Boolean>()
    val resultRecovery = MutableLiveData<Boolean>()

    fun sendBtc(asset: String, amount: String, address: String, otp: Editable) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponseSend(repository.sendBtcBalance(BtcBalance(asset, amount, address, otp.toString())))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun updateProfile(account: EditProfileRequest?) {
        if(account == null) return
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponseReceive(repository.updateUser(account))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponseSend(signUp: SignUpResponse) {
        if(signUp.result == true) {
            result.postValue(true)
            return
        }
        showError.postValue(signUp.error?.message)
    }

    private fun handleResponseReceive(address: SignUpResponse) {
        if(address.result == true) {
            resultRecovery.postValue(address.result)
            return
        }
        showError.postValue(address.error?.message)
    }
}
