package com.example.coinsliberty.ui.dialogs.adapter

import android.view.View
import com.example.coinsliberty.base.Holder
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.utils.extensions.invisible
import kotlinx.android.synthetic.main.dialog_change_language.view.*
import kotlinx.android.synthetic.main.item_change_language.view.*


object ChangeLanguageHolder {

    class ItemHolder : Holder<LanguageContent>() {
        override fun bind(itemView: View, item: LanguageContent) {
            if (!item.active) {
                itemView.rvChangeLanguage.ivCheck.invisible()
            }
            itemView.rvChangeLanguage.ivFlag.setImageResource(item.ico)
            itemView.rvChangeLanguage.tvName.setText(item.name)
        }
    }


}