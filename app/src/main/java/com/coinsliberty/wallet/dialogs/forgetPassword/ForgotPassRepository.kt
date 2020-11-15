package com.coinsliberty.wallet.dialogs.forgetPassword

import com.coinsliberty.wallet.api.LoginApi
import com.coinsliberty.wallet.data.ForgotPassRequest

class ForgotPassRepository(private val api: LoginApi) {

    suspend fun forgotPass(data: ForgotPassRequest) = api.passRecovery(data)
}