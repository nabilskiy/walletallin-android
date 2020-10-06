package com.example.coinsliberty.ui.dialogs

import android.os.Bundle
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.utils.extensions.bindDataTo
import org.koin.android.viewmodel.ext.android.viewModel


class ChangeLanguageDialog() : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_change_language
    override val viewModel: ChangeLanguageViewModel by viewModel()

    private var chosenLanguage: LanguageContent? = null

    private var currentLanguage: LanguageContent? = null
    private var languages: List<LanguageContent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeLiveData()
    }


    private fun subscribeLiveData() {
        bindDataTo(viewModel.languagesLiveData, ::initLanguages)
    }


    private fun initLanguages(list: List<LanguageContent>) {

    }
}