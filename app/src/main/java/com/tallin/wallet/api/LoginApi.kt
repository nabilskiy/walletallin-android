package com.tallin.wallet.api

import com.tallin.wallet.data.ForgotPassRequest
import com.tallin.wallet.data.LoginRequest
import com.tallin.wallet.data.SignUpRequest
import com.tallin.wallet.data.response.SignUpResponse
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

    @POST("/mapi/newaccesstoken")
    suspend fun login(
        @Body body: LoginRequest
    ): SignUpResponse
}