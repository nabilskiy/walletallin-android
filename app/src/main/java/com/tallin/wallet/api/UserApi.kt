package com.tallin.wallet.api

import com.tallin.wallet.data.requests.ChangePasswordRequest
import com.tallin.wallet.data.requests.EditProfileRequest
import com.tallin.wallet.data.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {
    @GET("/api/account")
    suspend fun getProfile(): ProfileResponse

    @POST("/mapi/account")
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

    @Multipart
    @POST("/api/upload")
    suspend fun sendFile(
        @Part body: MultipartBody.Part
    ): UploadResponse

    /*@POST("/api/exchange/get-rate")
    suspend fun getRate(
        @Body body: RateRequest
    ): RateResponse*/
}