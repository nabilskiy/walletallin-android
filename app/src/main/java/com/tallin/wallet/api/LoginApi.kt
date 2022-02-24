package com.tallin.wallet.api

import com.tallin.wallet.data.requests.ForgotPassRequest
import com.tallin.wallet.data.requests.LoginRequest
import com.tallin.wallet.data.requests.SignUpRequest
import com.tallin.wallet.data.response.SignUpResponse
import com.tallin.wallet.data.response.WalletTypesResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {

    @GET("/api/wallet/types")
    suspend fun walletTypes(): WalletTypesResponse

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