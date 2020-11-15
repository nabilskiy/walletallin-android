package com.coinsliberty.wallet.api

import com.coinsliberty.wallet.data.ForgotPassRequest
import com.coinsliberty.wallet.data.LoginRequest
import com.coinsliberty.wallet.data.SignUpRequest
import com.coinsliberty.wallet.data.response.SignUpResponse
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

    @POST("/api/newaccesstoken")
    suspend fun login(
        @Body body: LoginRequest
    ): SignUpResponse
}