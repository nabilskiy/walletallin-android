package com.example.coinsliberty.dialogs

import android.app.Application
import android.util.Log
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.data.ForgotPassRequest

class ForgotPassViewModel(
    app: Application,
    private val repository: ForgotPassRepository
) : BaseViewModel(app) {

    fun forgotPass(email: String) {
        launch(::onErrorHandler) {
            val response = repository.forgotPass(ForgotPassRequest(email))
            Log.e("!!!", response.toString())
        }
    }

}