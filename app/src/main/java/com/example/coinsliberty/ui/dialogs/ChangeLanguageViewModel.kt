package com.example.coinsliberty.ui.dialogs

import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.model.SharedPreferencesProvider

class ChangeLanguageViewModel(private val shared: SharedPreferencesProvider) : BaseViewModel() {

    val languagesLiveData: MutableLiveData<List<LanguageContent>> = MutableLiveData(getListData())
    //val currentLanguageLiveData: MutableLiveData<String> = MutableLiveData(shared.getLanguage().toString())

    fun getListData(): ArrayList<LanguageContent> {
        val listData: ArrayList<LanguageContent> = ArrayList()

        listData.add(LanguageContent(R.string.english, R.drawable.ic_unitedstates, false))
        listData.add(LanguageContent(R.string.russian, R.drawable.ic_russia, false))
        listData.add(LanguageContent(R.string.arabic, R.drawable.ic_united_arab_emirates, false))
        listData.add(LanguageContent(R.string.deutch, R.drawable.ic_germany, false))
        listData.add(LanguageContent(R.string.italiano, R.drawable.ic_italy, false))
        listData.add(LanguageContent(R.string.portuguese, R.drawable.ic_brazil, false))
        listData.add(LanguageContent(R.string.espanol, R.drawable.ic_spain, false))

        return listData
    }

//    fun setLanguage(languageContent: LanguageContent) {
//        shared.setLanguage(languageContent.name.toString())
//    }
}