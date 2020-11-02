package com.example.coinsliberty.ui.profile

import com.example.coinsliberty.api.UserApi
import com.example.coinsliberty.data.EditProfileRequest

class ProfileRepository(private val api: UserApi) {

    suspend fun editProfile(data: EditProfileRequest) = api.editProfile(data)
}