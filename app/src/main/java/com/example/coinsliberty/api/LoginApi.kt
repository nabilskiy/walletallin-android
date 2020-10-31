package com.example.coinsliberty.api

import com.example.coinsliberty.data.ForgotPassRequest
import com.example.coinsliberty.data.SignUpRequest
import com.example.coinsliberty.data.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/api/register")
    suspend fun signUp(
        @Body body: SignUpRequest
    ): SignUpResponse

    @POST("/api/request/passrecovery")
    suspend fun passRecovery(
        @Body body: ForgotPassRequest
    ): SignUpResponse

    @POST("/api/userLogin")
    suspend fun login(): Any
}