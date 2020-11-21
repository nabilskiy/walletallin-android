package com.coinsliberty.wallet.dialogs.faceIdDialog


import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.data.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FaceIdViewModel(
    app: Application,
    private val repository: FaceIdRepository
) : BaseViewModel(app) {

    val timeToDismiss: MutableLiveData<Boolean> = MutableLiveData()

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
        showError.postValue(response.error?.message)
    }

}