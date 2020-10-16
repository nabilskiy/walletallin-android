package com.example.coinsliberty.ui.dialogs

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.model.LanguageContent
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.item_change_language.view.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ChangeLanguageViewModel() : BaseViewModel() {

    val languagesLiveData: MutableLiveData<List<LanguageContent>> = MutableLiveData(getListData())
    val currentLanguagesLiveData: MutableLiveData<LanguageContent> = MutableLiveData()


    fun getListData(): ArrayList<LanguageContent> {
        val listData: ArrayList<LanguageContent> = ArrayList()

//        listData.add(LanguageContent(R.string.english, R.drawable.ic_unitedstates, false))
//        listData.add(LanguageContent(R.string.russian, R.drawable.ic_russia, false))
//        listData.add(LanguageContent(R.string.arabic, R.drawable.ic_united_arab_emirates, false))
//        listData.add(LanguageContent(R.string.deutch, R.drawable.ic_germany, false))
//        listData.add(LanguageContent(R.string.italiano, R.drawable.ic_italy, false))
//        listData.add(LanguageContent(R.string.portuguese, R.drawable.ic_brazil, false))
//        listData.add(LanguageContent(R.string.espanol, R.drawable.ic_spain, false))

        return listData
    }

    fun changeList(list: LanguageContent) {
        val listData: ArrayList<LanguageContent> = getListData()
        for (i in listData) {
            if (i.name.equals(list.name)) {
                Log.d("eee", currentLanguagesLiveData.toString())
                i.active = true
                currentLanguagesLiveData.postValue(i)
            }
        }
        languagesLiveData.postValue(listData)
    }


}