package com.example.coinsliberty.ui.dialogs

import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.model.SharedPreferencesProvider

class ChangeLanguageDialogViewModel(private val shared: SharedPreferencesProvider) :
    BaseViewModel() {

    val languagesLiveData: MutableLiveData<List<LanguageContent>> = MutableLiveData()
    val getCurrentLanguageLiveData: MutableLiveData<LanguageContent> = MutableLiveData()

    //val listLanguageContent: List<LanguageContent>? = null

    fun saveData(currentLanguage: LanguageContent?) {
        if (currentLanguage != null) {
            shared.setLanguage(currentLanguage)
        }
    }

    fun setListData(): ArrayList<LanguageContent> {
        val listData: ArrayList<LanguageContent> = ArrayList()

        listData.add(LanguageContent(1, R.string.english, R.drawable.ic_unitedstates, false))
        listData.add(LanguageContent(2, R.string.russian, R.drawable.ic_russia, false))
        listData.add(LanguageContent(3, R.string.arabic, R.drawable.ic_united_arab_emirates, false))
        listData.add(LanguageContent(4, R.string.deutch, R.drawable.ic_germany, false))
        listData.add(LanguageContent(5, R.string.italiano, R.drawable.ic_italy, false))
        listData.add(LanguageContent(6, R.string.portuguese, R.drawable.ic_brazil, false))
        listData.add(LanguageContent(7, R.string.espanol, R.drawable.ic_spain, false))

        //languagesLiveData.postValue(listData)
        languagesLiveData.postValue(shared.getLanguageList())
        return listData
    }

//     fun getLanguageList() {
//        getCurrentLanguageLiveData.postValue(shared.getLanguageList(setListData()))
//        splashUseCase.getLanguage()
//            .consumeOn(languagesLiveData)
//    }

    fun setCurrentLanguage() {

    }

}