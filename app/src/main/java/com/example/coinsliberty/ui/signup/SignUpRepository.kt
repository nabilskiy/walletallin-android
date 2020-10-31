package com.example.coinsliberty.ui.signup

import com.example.coinsliberty.api.LoginApi
import com.example.coinsliberty.data.SignUpRequest

class SignUpRepository(private val api: LoginApi) {

    suspend fun signUp(data: SignUpRequest) = api.signUp(data)
}