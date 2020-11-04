package com.example.coinsliberty.ui.settings

import com.example.coinsliberty.api.UserApi
import com.example.coinsliberty.data.EditProfileRequest

class SettingsRepository(private val api: UserApi) {

    suspend fun logout() = api.logout()
}