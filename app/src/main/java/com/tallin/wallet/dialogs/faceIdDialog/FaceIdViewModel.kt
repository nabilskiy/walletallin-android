package com.tallin.wallet.dialogs.faceIdDialog


import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FaceIdViewModel(
    app: Application,
    private val repository: FaceIdRepository,
    sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val timeToDismiss: MutableLiveData<Boolean> = MutableLiveData()


    override fun stopRequest() {

    }

    fun goScan(password: String, oldPassword: String) {
        launch(::onErrorHandler) {
            withContext(Dispatchers.Main) { onStartProgress.value = Unit }

            withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            //timeToDismiss.postValue(true)
        }
    }

    private fun handleResponse(response: SignUpResponse) {
        if (response.result == true) {
            timeToDismiss.postValue(true)
            return
        }
        showError.postValue(response.error?.message ?: "Error")
    }

}