package com.example.coinsliberty.ui.login

import com.example.coinsliberty.api.LoginApi
import com.example.coinsliberty.data.LoginRequest
import com.example.coinsliberty.data.SignUpRequest

class LoginRepository(private val api: LoginApi) {

    suspend fun login(data: LoginRequest) = api.login(data)
}