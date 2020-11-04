package com.example.coinsliberty.api

import com.example.coinsliberty.data.EditProfileRequest
import com.example.coinsliberty.data.EditProfileResponse
import com.example.coinsliberty.data.ChangePasswordRequest
import com.example.coinsliberty.data.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/api/account")
    suspend fun editProfile(
        @Body body: EditProfileRequest
    ): EditProfileResponse
    ////???

    @POST("/api/account/password")
    suspend fun changePassword(
        @Body body: ChangePasswordRequest
    ): SignUpResponse
}