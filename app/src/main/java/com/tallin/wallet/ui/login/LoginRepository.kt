package com.tallin.wallet.ui.login

import com.tallin.wallet.api.LoginApi
import com.tallin.wallet.api.UserApi
import com.tallin.wallet.data.requests.LoginRequest

class LoginRepository(private val api: LoginApi, private val api2: UserApi) {

    suspend fun login(data: LoginRequest) = api.login(data)
    suspend fun getProfile() = api2.getProfile()
}