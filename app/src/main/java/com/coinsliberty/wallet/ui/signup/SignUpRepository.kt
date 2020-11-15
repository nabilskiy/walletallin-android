package com.coinsliberty.wallet.ui.signup

import com.coinsliberty.wallet.api.LoginApi
import com.coinsliberty.wallet.data.SignUpRequest

class SignUpRepository(private val api: LoginApi) {

    suspend fun signUp(data: SignUpRequest) = api.signUp(data)
}