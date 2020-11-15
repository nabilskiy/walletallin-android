package com.coinsliberty.wallet.ui.login

import com.coinsliberty.wallet.api.LoginApi
import com.coinsliberty.wallet.data.LoginRequest

class LoginRepository(private val api: LoginApi) {

    suspend fun login(data: LoginRequest) = api.login(data)
}