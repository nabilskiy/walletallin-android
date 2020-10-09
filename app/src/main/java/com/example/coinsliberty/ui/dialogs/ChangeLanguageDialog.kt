package com.example.coinsliberty.ui.dialogs

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.ui.dialogs.adapter.ChangeLanguageHolder
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_change_language.*
import kotlinx.android.synthetic.main.item_change_language.*
import org.koin.android.viewmodel.ext.android.viewModel


class ChangeLanguageDialog() : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_change_language
    override val viewModel: ChangeLanguageViewModel by viewModel()
    var crLg: LanguageContent?=null

    val adapter = BaseAdapter()
        .map(R.layout.item_change_language, ChangeLanguageHolder.ItemHolder {
           crLg = it

//            clItemLanguage.setOnClickListener{
//               //setCurrentLanguage(crLg!!)
//            }

          //  it.active = it.name.toString() == viewModel.currentLanguageLiveData.toString()

            Toast.makeText(
                context,
                it.name,
                Toast.LENGTH_SHORT
            ).show()})

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()

        rvChangeLanguage.adapter = adapter

    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.languagesLiveData, ::initLanguages)
    }


    private fun initLanguages(list: List<LanguageContent>) {
        adapter.itemsLoaded(list)
    }


//    private fun setCurrentLanguage(languageContent: LanguageContent){
//        viewModel.setLanguage(languageContent)
//    }

    companion object {

        val TAG = ChangeLanguageDialog::class.java.name

    }
}