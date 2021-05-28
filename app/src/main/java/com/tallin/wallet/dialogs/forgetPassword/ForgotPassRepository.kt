package com.tallin.wallet.dialogs.forgetPassword

import com.tallin.wallet.api.LoginApi
import com.tallin.wallet.data.ForgotPassRequest

class ForgotPassRepository(private val api: LoginApi) {

    suspend fun forgotPass(data: ForgotPassRequest) = api.passRecovery(data)
}