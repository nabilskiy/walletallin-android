package com.example.coinsliberty.api

import com.example.coinsliberty.data.EditProfileRequest
import com.example.coinsliberty.data.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    ////???
    @POST("/api/account/password")
    suspend fun editProfile(
        @Body body: EditProfileRequest
    ): SignUpResponse
    ////???
}