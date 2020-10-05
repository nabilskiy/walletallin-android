package com.example.coinsliberty.ui.dialogs

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.di.viewModelModule
import com.example.coinsliberty.base.BaseViewModel
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.utils.extensions.bindDataTo
import org.koin.android.viewmodel.ext.android.viewModel


class ChangeLanguageDialog() : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_change_language
    override val viewModel: ChangeLanguageDialogViewModel by viewModel()

    private var chosenLanguage: LanguageContent? = null

    private var currentLanguage: LanguageContent? = null
    private var languages: List<LanguageContent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeLiveData()
    }


    private fun subscribeLiveData() {
        bindDataTo(viewModel.getCurrentLanguageLiveData, ::initLanguage)
        bindDataTo(viewModel.languagesLiveData, ::initLanguages)
    }


    private fun initLanguages(list: List<LanguageContent>) {
        languages = list
        chosenLanguage = list.first { it.name == currentLanguage?.name }
//        tvLanguage.setOnClickListener {
//            val popupMenu = PopupMenu(requireContext(), it)
//            popupMenu.inflate(R.menu.menu_language)
//            popupMenu.setOnMenuItemClickListener(this)
//            val menu: Menu = popupMenu.menu
//            for (i in 0 until menu.size()) {
//                val mi: MenuItem = menu.getItem(i)
//                applyFontToMenuItem(mi)
//            }
//            popupMenu.show()
//        }
    }

    private fun initLanguage(language: LanguageContent) {
        currentLanguage = language
//        tvLanguage.text = getString(LanguageTools.values().first { it.getShortName() == language.name}.getName())
    }
//
//
//    override fun onMenuItemClick(item: MenuItem?): Boolean {
//        tvLanguage.text = item?.title
//        when (item?.itemId) {
//            R.id.uk -> {
//                chosenLanguage = languages?.first { it.name == LanguageTools.UKRAINIAN.getShortName() }
//            }
//            R.id.ru -> {
//                chosenLanguage = languages?.first { it.name == LanguageTools.RUSSIAN.getShortName() }
//            }
//        }
//        return true
//    }
//
//    private fun applyFontToMenuItem(mi: MenuItem) {
//        val font = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            resources.getFont(R.font.century_gothic_regular)
//        } else {
//            ResourcesCompat.getFont(requireContext(), R.font.century_gothic_regular)
//        }
//        val mNewTitle = SpannableString(mi.title)
//        mNewTitle.setSpan(
//            CustomTypeFaceSpan("", font, Color.BLACK),
//            0,
//            mNewTitle.length,
//            Spannable.SPAN_INCLUSIVE_INCLUSIVE
//        )
//        mi.title = mNewTitle
//    }
//
//    private fun initCity(cityContent: CityContent) {
//        chosenCity = cityContent
//        actvValue.setText(cityContent.name)
//    }



}


//class ChangeLanguageDialog() : BaseKotlinDialogFragment() {
//    override val layoutRes: Int = R.layout.dialog_change_language
//    override val viewModel: ChangeLanguageDialogViewModel by viewM
//
//
//    //  override val navigator: ChooseTownNavigation = get()
//
//    private var choosenCity: CityContent? = null
//
//    private val disposer = CompositeDisposable()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        subscribeLiveData()
//    }
//    private fun subscribeLiveData() {
//        bindDataTo(viewModel.ldSignUp, ::prepareData)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initView()
//
////        viewModel.getChooseCity()
////        tvNext.setOnTouchListener { view, event ->
////            when (event.action){
////                MotionEvent.ACTION_DOWN -> {
////                    view.isSelected = true
////                }
////                MotionEvent.ACTION_UP -> {
////                    if(choosenCity != null && choosenCity?.name == actvValue.text.toString()) {
////                        viewModel.saveCity(choosenCity)
////                        navigator.goToMainScreen(navController)
////                    } else if(actvValue.text.toString().isEmpty()) {
////                        toast(getString(R.string.input_town_please))
////                        clTown.setBackgroundResource(R.drawable.bg_error_edit_text)
////                    } else {
////                        toast(getString(R.string.for_continue_you_need_choose_town_in_list))
////                        clTown.setBackgroundResource(R.drawable.bg_error_edit_text)
////                    }
////                    view.isSelected = false
////                }
////            }
////            true
////        }
////        actvValue.setOnKeyListener { v, keyCode, event ->
////            if (event.keyCode == KeyEvent.KEYCODE_ENTER) {
////                hideKeyboard(activity)
////            }
////            true
////        }
//    }
////
////    private fun subscribeLiveData() {
////        bindDataTo(viewModel.citysLiveData, ::initCities)
////    }
//
////    private fun initCities(list: List<CityContent>) {
////        val adapter = ArrayAdapter<String>(requireContext(),
////            android.R.layout.simple_dropdown_item_1line, list.map { it.toString() }.toTypedArray())
////        actvValue.setAdapter(adapter)
////        actvValue.setOnItemClickListener { _, _, position, _ ->
////            run {
////                choosenCity = list.first { it.name == adapter.getItem(position) }
////                hideKeyboard(activity)
////            }
////        }
////    }
//}