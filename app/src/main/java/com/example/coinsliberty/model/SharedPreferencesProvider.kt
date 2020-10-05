package com.example.coinsliberty.model

interface SharedPreferencesProvider {


    fun getLanguage(): LanguageContent?
    fun getLanguageList(): ArrayList<LanguageContent>
    fun setLanguage(lanId: LanguageContent?)

}
