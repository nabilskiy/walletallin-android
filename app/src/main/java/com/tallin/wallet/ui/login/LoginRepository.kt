package com.tallin.wallet.ui.login

import com.tallin.wallet.api.LoginApi
import com.tallin.wallet.data.LoginRequest

class LoginRepository(private val api: LoginApi) {

    suspend fun login(data: LoginRequest) = api.login(data)
}