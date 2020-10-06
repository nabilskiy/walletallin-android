package com.example.coinsliberty.ui.dialogs.adapter

import android.view.View
import com.example.coinsliberty.base.Holder
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.utils.extensions.invisible
import com.example.coinsliberty.utils.extensions.visibleIfOrInvisible
import kotlinx.android.synthetic.main.item_change_language.view.*


object ChangeLanguageHolder {

    class ItemHolder(private val onItemClick: (LanguageContent) -> Unit) : Holder<LanguageContent>() {
        override fun bind(itemView: View, item: LanguageContent) {

            itemView.ivCheck.visibleIfOrInvisible { item.active }
            itemView.ivFlag.setImageResource(item.ico)
            itemView.tvName.setText(item.name)
            itemView.rootView.setOnClickListener { onItemClick.invoke(item) }
        }
    }


}