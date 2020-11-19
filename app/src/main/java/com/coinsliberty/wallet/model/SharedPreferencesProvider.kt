package com.coinsliberty.wallet.model

import android.content.Context


class SharedPreferencesProvider(context: Context) {

    private val prefs = context.applicationContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)


    fun getLanguage() = prefs.getString(CURRENT_LANGUAGE, "")

    fun setLanguage(token: String) {
        prefs.edit().putString(CURRENT_LANGUAGE, token).apply()
    }

    fun getToken() = prefs.getString(AUTH_TOKEN_KEY, "")

    fun setToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN_KEY, token).apply()
    }

    fun canUseBiometric() = prefs.getBoolean(CAN_USE_BIOMETRIC, false)

    fun setUseBiometric(useBiometric: Boolean) {
        prefs.edit().putBoolean(CAN_USE_BIOMETRIC, useBiometric).apply()
    }

    fun getPin() = prefs.getString(PIN_CODE, "")

    fun savePin(pin: String) {
        prefs.edit().putString(PIN_CODE, pin).apply()
    }

    companion object {
        private const val SHARED_PREFS = "coinsLiberty"
        private const val AUTH_TOKEN_KEY = "auth_TokenKey"
        private const val CURRENT_LANGUAGE = "English"
        private const val CAN_USE_BIOMETRIC = "canUseBiomentric"
        private const val PIN_CODE = "pinCode"
    }

}