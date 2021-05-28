package com.tallin.wallet.ui.settings

import com.tallin.wallet.api.UserApi

class SettingsRepository(private val api: UserApi) {

    suspend fun logout() = api.logout()
}