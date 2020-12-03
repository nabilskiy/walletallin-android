package com.coinsliberty.wallet.dialogs.makeTransaction

import android.app.Application
import android.text.Editable
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.response.*
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MakeTransactionViewModel(
    private val app: Application,
    private val repository: MakeTransactionRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val result = MutableLiveData<Boolean>()
    val resultRecovery = MutableLiveData<String>()
    val feeInit = MutableLiveData<Rates>()
    val messageError = MutableLiveData<String>()
    val maxAvailable = MutableLiveData<SendMaxResponse>()

    fun sendBtc(asset: String, amount: String, address: String, otp: Editable, fee: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponseSend(
                repository.sendBtcBalance(
                    BtcBalance(
                        asset,
                        amount,
                        address,
                        otp.toString(),
                        fee
                    )
                )
            )
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun updateData() {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponseReceive(repository.getAddress())
            handleResponseFee(repository.getFee())
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    fun refreshFee() {
        launch(::onErrorHandler) {
            delay(5000)
            handleResponseFee(repository.getFee())
        }
    }

    fun sendMax(asset: String, rate: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponseSendMax(repository.sendMax(asset, rate))
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }

    private fun handleResponseFee(fee: FeeResponse) {
        feeInit.postValue(fee.rates)
    }

    private fun handleResponseSendMax(sendMax: SendMaxResponse) {
        maxAvailable.postValue(sendMax)
    }

    private fun handleResponseSend(signUp: SignUpResponse) {
        if (signUp.result == true) {
            result.postValue(true)
            return
        } else {
            result.postValue(false)
            messageError.postValue(signUp.error?.message.toString())
            // showError.postValue(signUp.error?.message)
            return
        }
    }

    private fun handleResponseReceive(address: AddressInfoResponse) {
        if (address.result == true) {
            resultRecovery.postValue(address.address)
            return
        }
        showError.postValue(address.error?.message)
    }
}
