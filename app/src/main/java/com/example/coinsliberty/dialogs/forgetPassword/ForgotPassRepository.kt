package com.example.coinsliberty.dialogs.forgetPassword

import com.example.coinsliberty.api.LoginApi
import com.example.coinsliberty.data.ForgotPassRequest

class ForgotPassRepository(private val api: LoginApi) {

    suspend fun forgotPass(data: ForgotPassRequest) = api.passRecovery(data)
}