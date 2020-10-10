package com.example.coinsliberty.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.di.viewModelModule
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.ui.dialogs.adapter.ChangeLanguageHolder
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_change_language.*
import kotlinx.android.synthetic.main.item_change_language.*
import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel


class ChangeLanguageDialog() : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_change_language
    override val viewModel: ChangeLanguageViewModel by viewModel()

//    interface NoticeDialogListener {
//        fun onDialogPositiveClick(dialog: BaseKotlinDialogFragment?)
//    }
    //var mListener: NoticeDialogListener? = null

    @SuppressLint("ResourceAsColor")
    val adapter = BaseAdapter()
        .map(R.layout.item_change_language, ChangeLanguageHolder.ItemHolder {
            viewModel.changeList(it)
            if (it.active) { tvName.setTextColor(R.color.darkBlue) }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()
        ivClose.setOnClickListener { dismiss() }
        rvChangeLanguage.adapter = adapter
    }


//    override fun onAttach(activity: Activity) {
//        super.onAttach(activity)
//        mListener = try {
//            activity as NoticeDialogListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException(
//                activity.toString()
//                        + " must implement NoticeDialogListener"
//            )
//        }
//    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.languagesLiveData, ::initLanguages)
        //bindDataTo(viewModel.currentLanguagesLiveData, ::changeLanguage)
    }


    private fun initLanguages(list: List<LanguageContent>) {
        adapter.itemsLoaded(list)
    }

    // private fun changeLanguage(item: LanguageContent) {}

    companion object {
        val TAG = ChangeLanguageDialog::class.java.name
        fun newInstance(): DialogFragment {
            val fragment = ChangeLanguageDialog()
            fragment.setStyle(STYLE_NO_FRAME, 0)
            return fragment
        }
    }
}