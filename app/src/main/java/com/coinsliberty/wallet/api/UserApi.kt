package com.coinsliberty.wallet.api

import com.coinsliberty.wallet.data.*
import com.coinsliberty.wallet.data.response.ProfileResponse
import com.coinsliberty.wallet.data.response.SignUpResponse
import com.coinsliberty.wallet.data.response.TokenResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @GET("/api/account")
    suspend fun getProfile(): ProfileResponse

    @POST("/api/account")
    suspend fun editProfile(
        @Body body: EditProfileRequest
    ): SignUpResponse

    @POST("/api/account/password")
    suspend fun changePassword(
        @Body body: ChangePasswordRequest
    ): SignUpResponse

    @POST("/api/logout")
    suspend fun logout(): SignUpResponse

    @GET("/api/account/otp")
    suspend fun getOtp(): TokenResponse
}