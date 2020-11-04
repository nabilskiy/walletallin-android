package com.example.coinsliberty.api

import com.example.coinsliberty.data.*
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
    ////???

    @POST("/api/account/password")
    suspend fun changePassword(
        @Body body: ChangePasswordRequest
    ): SignUpResponse

    @POST("/api/logout")
    suspend fun logout(): SignUpResponse
}