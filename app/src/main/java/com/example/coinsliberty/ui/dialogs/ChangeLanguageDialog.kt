package com.example.coinsliberty.ui.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseAdapter
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.di.viewModelModule
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.ui.dialogs.adapter.ChangeLanguageHolder
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.item_change_language.*
//import org.koin.android.experimental.dsl.viewModel
import org.koin.android.viewmodel.ext.android.viewModel


class ChangeLanguageDialog() : BaseKotlinDialogFragment() {
    override val layoutRes: Int = 0
    override val viewModel: ChangeLanguageViewModel by viewModel()

    private var listener: ((Boolean) -> Unit)? = null

//    interface NoticeDialogListener {
//        fun onDialogPositiveClick(dialog: BaseKotlinDialogFragment?)
//    }
//    var mListener: NoticeDialogListener? = null

    @SuppressLint("ResourceAsColor")
    var adapter = BaseAdapter()
        .map(R.layout.item_change_language, ChangeLanguageHolder.ItemHolder {
            viewModel.changeList(it)
//            clItemLang.setOnClickListener {
//                listener?.invoke(true)
//            }
            if (it.active) { tvName.setTextColor(R.color.darkBlue) }
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()

//        ivClose.setOnClickListener { dismiss() }
//        rvChangeLanguage.adapter = adapter

    }
//    @SuppressLint("ResourceAsColor")
//    override fun onStart() {
//        super.onStart()
//        adapter = BaseAdapter()
//            .map(R.layout.item_change_language, ChangeLanguageHolder.ItemHolder {
//                viewModel.changeList(it)
//                clItemLang.setOnClickListener {
//                    listener?.invoke(true)
//                }
//                if (it.active) { tvName.setTextColor(R.color.darkBlue) }
//            })
//        subscribeLiveData()
//        rvChangeLanguage.adapter = adapter
//
//    }
    fun initListeners(onChecked: (Boolean) -> Unit) {
        listener = onChecked
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
        bindDataTo(viewModel.currentLanguagesLiveData, ::changeLanguage)
    }


    private fun initLanguages(list: List<LanguageContent>) {
        adapter.itemsLoaded(list)
    }

     private fun changeLanguage(item: LanguageContent) {
         Log.d("eee", item.toString())

     }

    companion object {
        val TAG = ChangeLanguageDialog::class.java.name
        fun newInstance(): DialogFragment {
            val fragment = ChangeLanguageDialog()
            fragment.setStyle(STYLE_NO_FRAME, 0)
            return fragment
        }
    }
}