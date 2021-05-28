package com.tallin.wallet.ui.signup

import com.tallin.wallet.api.LoginApi
import com.tallin.wallet.data.SignUpRequest

class SignUpRepository(private val api: LoginApi) {

    suspend fun signUp(data: SignUpRequest) = api.signUp(data)
}