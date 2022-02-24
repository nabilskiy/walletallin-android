package com.tallin.wallet.ui.singup.signup

import com.tallin.wallet.api.LoginApi
import com.tallin.wallet.data.requests.SignUpRequest

class SignUpRepository(private val api: LoginApi) {

    suspend fun signUp(data: SignUpRequest) = api.signUp(data)
}