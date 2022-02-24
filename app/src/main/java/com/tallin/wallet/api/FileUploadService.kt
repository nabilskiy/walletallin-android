package com.tallin.wallet.api

import com.tallin.wallet.data.requests.EditProfileRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part


interface FileUploadService {
    @Multipart
    @POST("upload")
    fun upload(
        @Part("description") description: RequestBody?,
        @Part file: MultipartBody.Part?
    ): ResponseBody


    @Multipart
    @POST("/api/Accounts/editaccount")
    fun editUser(
        @Part("file\"; filename=\"pp.png\" ") file: RequestBody?,
        @Part("firstName") first_name: EditProfileRequest?,
        @Part("lastName") last_name: EditProfileRequest?,
        @Part("phone") phone: EditProfileRequest?,
        @Part("optEnabled") opt_enabled: EditProfileRequest?,
    ): ResponseBody
}