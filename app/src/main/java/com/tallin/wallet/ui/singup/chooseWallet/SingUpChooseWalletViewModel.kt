package com.tallin.wallet.ui.singup.chooseWallet

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.WalletTypesResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class SingUpChooseWalletViewModel(
    app: Application,
    private val repository: SingUpChooseWalletRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val result = MutableLiveData<WalletTypesResponse>()

    var getWalletTypesJob: Job? = null

    fun getWalletTypes(){
        getWalletTypesJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }
            handleResponse(repository.getWalletTypes())
            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
        }
    }
    override fun stopRequest() {
        getWalletTypesJob?.cancel()
    }

    private fun handleResponse(walletTypes: WalletTypesResponse) {
        if(walletTypes.result == true) {
            result.postValue(walletTypes)
           // anyData.postValue(walletTypes.data)
            return
        }

        showError.postValue("Error")
    }

}