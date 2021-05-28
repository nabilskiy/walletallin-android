package com.tallin.wallet.ui.dialogs

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseAdapter
import com.tallin.wallet.base.BaseKotlinDialogFragment
import com.tallin.wallet.model.LanguageContent
import com.tallin.wallet.ui.dialogs.adapter.ChangeLanguageHolder
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_change_language.*
import org.koin.android.viewmodel.ext.android.viewModel


class ChangeLanguageDialog() : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_change_language
    override val viewModel: ChangeLanguageViewModel by viewModel()

    var activeIcon: Int? = null

    var listener: ((LanguageContent) -> Unit)? = null

    val adapter = BaseAdapter()
        .map(R.layout.item_change_language, ChangeLanguageHolder.ItemHolder {
            listener?.invoke(it)
            viewModel.changeList(it)
        })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeLiveData()
        ivClose.setOnClickListener { dismiss() }
        rvChangeLanguage.adapter = adapter
    }

    fun initListeners(onChoosen: (LanguageContent) -> Unit) {
        listener = onChoosen
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.languagesLiveData, ::initLanguages)
    }

    private fun initLanguages(list: List<LanguageContent>) {
        if (list.none { it.active }) {
            list.map { it.apply { it.checkActive(activeIcon) } }
        }

        adapter.itemsLoaded(list)
    }

    companion object {
        val TAG = ChangeLanguageDialog::class.java.name

        fun newInstance(icon: Int): ChangeLanguageDialog {
            val fragment = ChangeLanguageDialog()
            fragment.activeIcon = icon
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogWhiteBG)
            return fragment
        }
    }
}