package com.tallin.wallet.ui.dialogs.adapter

import android.view.View
import com.tallin.wallet.base.Holder
import com.tallin.wallet.model.LanguageContent
import com.tallin.wallet.utils.extensions.visibleIfOrInvisible
import kotlinx.android.synthetic.main.item_change_language.view.*

object ChangeLanguageHolder {

    class ItemHolder(private val onItemClick: (LanguageContent) -> Unit) :
        Holder<LanguageContent>() {
        override fun bind(itemView: View, item: LanguageContent) {
            itemView.ivCheck.visibleIfOrInvisible { item.active }
            itemView.tvName.isSelected =  item.active
            itemView.ivFlag.setImageResource(item.ico)
            itemView.tvName.setText(item.name)
            itemView.rootView.setOnClickListener {
                onItemClick.invoke(item)
            }
        }
    }


}