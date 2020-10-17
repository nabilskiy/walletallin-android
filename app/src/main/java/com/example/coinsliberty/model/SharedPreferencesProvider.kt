package com.example.coinsliberty.model

import android.content.Context


class SharedPreferencesProvider(context: Context) {

    private val prefs = context.applicationContext.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)


    fun getLanguage() = prefs.getString(CURRENT_LANGUAGE, "")

    fun setLanguage(token: String) {
        prefs.edit().putString(CURRENT_LANGUAGE, token).apply()
    }


    companion object {
        private const val SHARED_PREFS = "coinsLiberty"
        private const val CURRENT_LANGUAGE = "English"
        private const val NEED_PERMISSION = "needPermission"
    }

}