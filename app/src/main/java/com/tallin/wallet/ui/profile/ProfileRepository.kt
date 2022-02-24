package com.tallin.wallet.ui.profile

import com.tallin.wallet.api.UserApi
import com.tallin.wallet.data.requests.EditProfileRequest
import okhttp3.MultipartBody

class ProfileRepository(private val api: UserApi) {

    suspend fun getProfile() = api.getProfile()

    suspend fun getOtp() = api.getOtp()

    suspend fun editProfile(data: EditProfileRequest) = api.editProfile(data)

    suspend fun sendFile(body: MultipartBody.Part) = api.sendFile(body)
}