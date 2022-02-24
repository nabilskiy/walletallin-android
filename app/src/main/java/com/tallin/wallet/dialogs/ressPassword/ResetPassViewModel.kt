package com.tallin.wallet.dialogs.ressPassword

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.requests.ChangePasswordRequest
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext

class ResetPassViewModel(
    app: Application,
    private val repository: ResetPassRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val timeToDismiss : MutableLiveData<Boolean> = MutableLiveData()

    var changePasswordJob: Job? = null

    override fun stopRequest() {
        changePasswordJob?.cancel()
    }

    fun changePassword(password: String, oldPassword: String) {
        changePasswordJob = launch(::onErrorHandler) {
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

        showError.postValue(response.error?.message ?: "Error")
    }

}