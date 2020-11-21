package com.coinsliberty.wallet.dialogs.makeTransaction

import android.app.Application
import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.EditProfileRequest
import com.coinsliberty.wallet.data.response.AddressInfoResponse
import com.coinsliberty.wallet.data.response.FeeResponse
import com.coinsliberty.wallet.data.response.Rates
import com.coinsliberty.wallet.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MakeTransactionViewModel (
    private val app: Application,
    private val repository: MakeTransactionRepository
): BaseViewModel(app) {

    val result = MutableLiveData<Boolean>()
    val resultRecovery = MutableLiveData<String>()
    val feeInit = MutableLiveData<Rates>()
    val messageError = MutableLiveData<String>()

    fun sendBtc(asset: String, amount: String, address: String, otp: Editable, fee: String) {
        launch(::onErrorHandler) {

            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponseSend(repository.sendBtcBalance(BtcBalance(asset, amount, address, otp.toString(), fee)))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    fun updateData() {
        Log.e("!!!", "test")
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponseReceive(repository.getAddress())
            handleResponseFee(repository.getFee())
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponseFee(fee: FeeResponse) {
        feeInit.postValue(fee.rates)
    }

    private fun handleResponseSend(signUp: SignUpResponse) {
        if(signUp.result == true) {
            result.postValue(true)
            return
        }else{
            result.postValue(false)
            messageError.postValue(signUp.error?.message.toString())
           // showError.postValue(signUp.error?.message)
            return
        }

    }

    private fun handleResponseReceive(address: AddressInfoResponse) {
        if(address.result == true) {
            resultRecovery.postValue(address.address)
            return
        }
        showError.postValue(address.error?.message)
    }
}
