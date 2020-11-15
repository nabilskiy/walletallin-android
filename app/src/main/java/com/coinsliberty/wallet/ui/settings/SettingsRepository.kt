package com.coinsliberty.wallet.ui.settings

import com.coinsliberty.wallet.api.UserApi

class SettingsRepository(private val api: UserApi) {

    suspend fun logout() = api.logout()
}