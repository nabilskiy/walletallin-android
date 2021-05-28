package com.tallin.wallet.dialogs.secureCode

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.EditProfileRequest
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class SecureCodeViewModel(
    app: Application,
    private val repository: SecureCodeRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val resultRecovery = MutableLiveData<Boolean>()

    var updateProfileJob: Job? = null

    override fun stopRequest() {
        updateProfileJob?.cancel()
    }

    fun updateProfile(account: EditProfileRequest?) {
        if(account == null) return
        updateProfileJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            handleResponse(repository.updateUser(account))
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
        }
    }

    private fun handleResponse(address: SignUpResponse) {
        if(address.result == true) {
            resultRecovery.postValue(address.result)
            return
        }
        showError.postValue(address.error?.message ?: "Error")
    }

}