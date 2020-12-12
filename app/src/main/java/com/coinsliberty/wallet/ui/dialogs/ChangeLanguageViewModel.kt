package com.coinsliberty.wallet.ui.dialogs

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseViewModel
import com.coinsliberty.wallet.model.LanguageContent
import com.coinsliberty.wallet.model.SharedPreferencesProvider
import com.coinsliberty.wallet.ui.login.LoginRepository

class ChangeLanguageViewModel(
    private val app: Application,
    sharedPreferencesProvider: SharedPreferencesProvider,
    loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val languagesLiveData: MutableLiveData<List<LanguageContent>> = MutableLiveData(getListData())
    val currentLanguagesLiveData: MutableLiveData<LanguageContent> = MutableLiveData()


    override fun stopRequest() {

    }

    private fun getListData(): ArrayList<LanguageContent> {
        val listData: ArrayList<LanguageContent> = ArrayList()

        listData.add(LanguageContent(R.string.english, R.drawable.ic_unitedstates))
        listData.add(LanguageContent(R.string.russian, R.drawable.ic_russia))
        listData.add(LanguageContent(R.string.arabic, R.drawable.ic_united_arab_emirates))
        listData.add(LanguageContent(R.string.deutch, R.drawable.ic_germany))
        listData.add(LanguageContent(R.string.italiano, R.drawable.ic_italy))
        listData.add(LanguageContent(R.string.portuguese, R.drawable.ic_brazil))
        listData.add(LanguageContent(R.string.espanol, R.drawable.ic_spain))

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