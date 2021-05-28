package com.tallin.wallet.model

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.tallin.wallet.utils.currency.Currency


class SharedPreferencesProvider(context: Context) {

    var masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs =
        EncryptedSharedPreferences.create(
            context,
            "coinsLiberty",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )



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

    fun getLogin() = prefs.getString(LOGIN, "")

    fun saveLogin(login: String) {
        prefs.edit().putString(LOGIN, login).apply()
    }

    fun getCurrency(): Currency {
        val currency = prefs.getString(CURRENCY, "")
        if (currency.isNullOrEmpty()) {
            return Currency.USD
        }

        return Currency.values().firstOrNull { currency == it.getTitle() } ?: Currency.USD
    }

    fun saveCurrency(currency: Currency) {
        prefs.edit().putString(CURRENCY, currency.getTitle()).apply()
    }

    fun getPassword() = prefs.getString(PASSWORD, "")

    fun savePassword(pin: String) {
        prefs.edit().putString(PASSWORD, pin).apply()
    }


    companion object {
        private const val SHARED_PREFS = "coinsLiberty"
        private const val AUTH_TOKEN_KEY = "auth_TokenKey"
        private const val CURRENT_LANGUAGE = "English"
        private const val CAN_USE_BIOMETRIC = "canUseBiomentric"
        private const val LOGIN = "login"
        private const val PASSWORD = "password"
        private const val PIN_CODE = "pinCode"
        private const val CURRENCY = "currency"
    }

}