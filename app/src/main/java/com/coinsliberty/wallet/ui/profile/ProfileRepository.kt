package com.coinsliberty.wallet.ui.profile

import com.coinsliberty.wallet.api.UserApi
import com.coinsliberty.wallet.data.EditProfileRequest
import okhttp3.MultipartBody

class ProfileRepository(private val api: UserApi) {

    suspend fun getProfile() = api.getProfile()

    suspend fun getOtp() = api.getOtp()

    suspend fun editProfile(data: EditProfileRequest) = api.editProfile(data)

    suspend fun sendFile(body: MultipartBody.Part) = api.sendFile(body)
}